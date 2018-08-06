package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Stream;

import static java.util.stream.StreamSupport.stream;

public class XLSFormFactory implements com.github.batkinson.jxlsform.api.XLSFormFactory {

    private com.github.batkinson.jxlsform.api.SurveyItemFactory itemFactory;

    public XLSFormFactory() {
        this(new SurveyItemFactory());
    }

    XLSFormFactory(SurveyItemFactory itemFactory) {
        this.itemFactory = itemFactory;
    }

    @Override
    public com.github.batkinson.jxlsform.api.XLSForm create(com.github.batkinson.jxlsform.api.Workbook workbook) {
        XLSForm form = new XLSForm(workbook);
        populateChoices(form);
        populateSurvey(form);
        return form;
    }

    private void populateChoices(XLSForm form) {

        Choices choices = form.getChoices();

        // keep common list references, so we can add to lists
        Map<String, ChoiceList> lists = new LinkedHashMap<>();

        // construct the choice lists
        stream(choices.getSheet().spliterator(), false)
                .filter(row -> !row.isHeader())
                .map(row -> row.getCellByHeader("list_name"))
                .flatMap(cell -> cell.map(com.github.batkinson.jxlsform.api.Cell::getValue)
                        .map(Stream::of)
                        .orElseGet(Stream::empty))
                .distinct()
                .map(listName -> new ChoiceList(choices, listName))
                .forEachOrdered(list -> {
                    choices.addList(list);
                    lists.put(list.getName(), list);
                });

        // add the choices
        stream(choices.getSheet().spliterator(), false)
                .filter(row -> !row.isHeader())
                .filter(row -> row.getCellByHeader("list_name")
                        .map(com.github.batkinson.jxlsform.api.Cell::getValue).isPresent())
                .forEachOrdered(row -> {
                    String listName = row.getCellByHeader("list_name")
                            .map(com.github.batkinson.jxlsform.api.Cell::getValue).orElse("");
                    ChoiceList list = lists.get(listName);
                    list.add(new Choice(list, row));
                });
    }

    private void populateSurvey(XLSForm form) {

        Survey survey = form.getSurvey();
        Stack<SurveyItemContainer> groupStack = new Stack<>();
        groupStack.push(survey);

        stream(survey.getSheet().spliterator(), false)
                .filter(row -> !row.isHeader())
                .filter(row -> row.getCellByHeader("type")
                        .map(com.github.batkinson.jxlsform.api.Cell::getValue)
                        .isPresent())
                .forEachOrdered(row -> {
                    String type = row.getCellByHeader("type")
                            .map(com.github.batkinson.jxlsform.api.Cell::getValue)
                            .orElse("");
                    switch (type) {
                        case "begin group":
                            groupStack.push(new Group(survey, groupStack.peek(), row));
                            break;
                        case "end group":
                            if (!(groupStack.peek() instanceof Group)) {
                                throw new XLSFormException("unexpected 'end group', " + row);
                            }
                            Group closedGroup = (Group) groupStack.pop();
                            groupStack.peek().add(closedGroup);
                            break;
                        case "begin repeat":
                            groupStack.push(new Repeat(survey, groupStack.peek(), row));
                            break;
                        case "end repeat":
                            if (!(groupStack.peek() instanceof Repeat)) {
                                throw new XLSFormException("unexpected 'end repeat', " + row);
                            }
                            Repeat closedRepeat = (Repeat) groupStack.pop();
                            groupStack.peek().add(closedRepeat);
                            break;
                        default:
                            itemFactory.create(survey, groupStack.peek(), row)
                                    .ifPresent(item -> groupStack.peek().add(item));
                    }
                });

        if (groupStack.size() > 1) {
            throw new XLSFormException("unclosed group or repeat " + groupStack.peek());
        }
    }
}
