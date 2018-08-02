package com.github.batkinson.jxlsform.common;

import com.github.batkinson.jxlsform.api.Cell;
import com.github.batkinson.jxlsform.api.XLSFormException;

import java.util.Stack;
import java.util.stream.StreamSupport;

public class XLSFormFactory implements com.github.batkinson.jxlsform.api.XLSFormFactory {

    @Override
    public com.github.batkinson.jxlsform.api.XLSForm create(com.github.batkinson.jxlsform.api.Workbook workbook) {

        XLSForm form = new XLSForm(workbook);
        Survey survey = form.getSurvey();

        Stack<SurveyItemContainer> groupStack = new Stack<>();
        groupStack.push(survey);

        StreamSupport
                .stream(survey.getSheet().spliterator(), false)
                .filter(row -> !row.isHeader())
                .filter(row -> row.getCellByHeader("type").isPresent())
                .forEachOrdered(row -> {
                    String type = row.getCellByHeader("type").map(Cell::getValue).orElse("");
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
                            groupStack.peek().add(new Question(survey, groupStack.peek(), row));
                    }
                });

        if (groupStack.size() > 1) {
            throw new XLSFormException("unclosed group or repeat " + groupStack.peek());
        }

        return form;
    }
}
