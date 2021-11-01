package com.iwell.eye.common.base;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

@Data
public class CommonBaseVO implements Serializable  {
	private static final long serialVersionUID = 1L;
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

}
