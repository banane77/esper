/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.common.internal.epl.expression.dot.inner;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.internal.epl.expression.core.ExprEnumerationEval;
import com.espertech.esper.common.internal.epl.expression.core.ExprEvaluatorContext;
import com.espertech.esper.common.internal.epl.expression.dot.core.ExprDotEvalRootChildInnerEval;

import java.util.Collection;

public class InnerDotEnumerableEventCollectionEval implements ExprDotEvalRootChildInnerEval {

    private final ExprEnumerationEval rootLambdaEvaluator;

    public InnerDotEnumerableEventCollectionEval(ExprEnumerationEval rootLambdaEvaluator) {
        this.rootLambdaEvaluator = rootLambdaEvaluator;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext) {
        return rootLambdaEvaluator.evaluateGetROCollectionEvents(eventsPerStream, isNewData, exprEvaluatorContext);
    }

    public Collection<EventBean> evaluateGetROCollectionEvents(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return rootLambdaEvaluator.evaluateGetROCollectionEvents(eventsPerStream, isNewData, context);
    }

    public Collection evaluateGetROCollectionScalar(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return rootLambdaEvaluator.evaluateGetROCollectionEvents(eventsPerStream, isNewData, context);
    }

    public EventBean evaluateGetEventBean(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context) {
        return null;
    }

}
