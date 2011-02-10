package com.tomecode.soa.dependency.analyzer.gui.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * (c) Copyright Tomecode.com, 2010. All rights reserved.
 * 
 * adding data from object to parent {@link PropertyGroupView}
 * 
 * @author Tomas Frastia
 * @see http://www.tomecode.com
 *      http://code.google.com/p/bpel-esb-dependency-analyzer/
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface AddDataToExistsGroupView {

}
