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
package com.espertech.esper.regressionlib.support.context;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.regressionlib.framework.RegressionEnvironment;
import com.espertech.esper.runtime.client.EPStatement;

import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SupportContextPropUtil {
    public static void assertContextProps(RegressionEnvironment env, String stmtName, String contextName, int[] ids, String fieldsCSV, Object[][] values) {
        if (fieldsCSV != null) {
            assertEquals(ids.length, values.length);
        } else {
            assertNull(values);
        }

        EPStatement stmt = env.statement(stmtName);
        if (stmt == null) {
            fail("Cannot find statement '" + stmtName + "'");
        }

        int num = -1;
        for (int id : ids) {
            num++;
            Map<String, Object> props = env.runtime().getContextPartitionService().getContextProperties(stmt.getDeploymentId(), contextName, id);
            assertProps(id, contextName, props, fieldsCSV, values == null ? null : values[num], true);
        }
    }

    /**
     * Values:
     * - by id first
     * - by level second
     * - by field third
     */
    public static void assertContextPropsNested(RegressionEnvironment env, String stmtName, String contextName, int[] ids, String[] nestedContextNames, String[] fieldsCSVPerCtx, Object[][][] values) {
        EPStatement stmt = env.statement(stmtName);
        if (stmt == null) {
            fail("Cannot find statement '" + stmtName + "'");
        }

        int line = -1;
        for (int id : ids) {
            line++;
            Map<String, Object> props = env.runtime().getContextPartitionService().getContextProperties(stmt.getDeploymentId(), contextName, id);
            assertEquals(contextName, props.get("name"));
            assertEquals(id, props.get("id"));

            assertEquals(nestedContextNames.length, fieldsCSVPerCtx.length);
            for (int level = 0; level < nestedContextNames.length; level++) {
                assertProps(id, nestedContextNames[level], (Map<String, Object>) props.get(nestedContextNames[level]), fieldsCSVPerCtx[level], values[line][level], false);
            }
        }
    }

    private static void assertProps(int id, String contextName, Map<String, Object> props, String fieldsCSV, Object[] values, boolean assertId) {
        String[] fields = fieldsCSV == null ? new String[0] : fieldsCSV.split(",");

        if (values != null) {
            assertEquals(values.length, fields.length);
        }
        assertEquals(contextName, props.get("name"));
        if (assertId) {
            assertEquals(id, props.get("id"));
        }

        int col = -1;
        for (String field : fields) {
            col++;
            Object expected = values[col];
            Object actual = props.get(field);
            if (actual instanceof EventBean) {
                actual = ((EventBean) actual).getUnderlying();
            }
            assertEquals("Mismatch id " + id + " field " + field, expected, actual);
        }
    }
}
