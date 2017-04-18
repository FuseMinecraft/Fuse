package com.fusenetworks.fuse.commands;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//Credit to TF
public @interface CommandPermissions
{
    SourceType source();
    boolean blockHostConsole() default false;
}