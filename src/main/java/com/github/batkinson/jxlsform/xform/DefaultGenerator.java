package com.github.batkinson.jxlsform.xform;

import com.github.batkinson.jxlsform.api.Settings;
import com.github.batkinson.jxlsform.api.XLSForm;
import com.github.batkinson.jxlsform.api.XLSFormException;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultGenerator implements Generator {

    private Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);

    public DefaultGenerator() {
        cfg.setClassLoaderForTemplateLoading(DefaultGenerator.class.getClassLoader(),
                DefaultGenerator.class.getPackage().getName().replaceAll("[.]", "/"));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
    }

    @Override
    public void generateXForm(XLSForm xlsform, Writer writer) throws IOException, XLSFormException {

        Map<String, Object> model = new HashMap<>();

        String formId = xlsform.getSettings().flatMap(Settings::getFormId)
                .orElseThrow(() -> new XLSFormException("form id required"));

        model.put("formId", formId);
        model.put("formTitle", xlsform.getSettings().flatMap(Settings::getFormTitle).orElse(formId));
        model.put("formVersion", xlsform.getSettings().flatMap(Settings::getFormVersion).orElse("1"));
        model.put("instanceName", "data");

        List<Item> items = xlsform.getSurvey().stream()
                .map(Item::new)
                .collect(Collectors.toList());

        model.put("elements", items.stream()
                .map(Item::getElement)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        model.put("binds", items.stream()
                .map(Item::getBinding)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        model.put("controls", items.stream()
                .map(Item::getControl)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));

        try {
            cfg.getTemplate("xform.ftlx").process(model, writer);
        } catch (TemplateException e) {
            throw new XLSFormException("failed to generate xform using template", e);
        }
    }
}

