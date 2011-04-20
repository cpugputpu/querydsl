package com.mysema.query.maven;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Collections;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.junit.Test;


public class TestMetadataExportMojoTest {
    
    private final String url = "jdbc:h2:mem:testdb" + System.currentTimeMillis();

    @Test
    public void Execute() throws SecurityException, NoSuchFieldException, IllegalAccessException, MojoExecutionException, MojoFailureException {
        MavenProject project = new MavenProject();
        TestMetadataExportMojo mojo = new TestMetadataExportMojo();
        set(mojo, "project", project);
        set(mojo, "jdbcDriver", "org.h2.Driver");
        set(mojo, "jdbcUrl", url);
        set(mojo, "jdbcUser", "sa");
        set(mojo, "namePrefix", "Q"); // default value
        set(mojo, "nameSuffix", "");
        set(mojo, "beanPrefix", "");
        set(mojo, "beanSuffix", "Bean");
        set(mojo, "packageName", "com.example");
        set(mojo, "targetFolder", "target/export2");

        mojo.execute();

        assertEquals(Collections.singletonList("target/export2"), project.getTestCompileSourceRoots());
        assertTrue(new File("target/export2").exists());
    }

    private void set(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalAccessException{
        Field field = AbstractMetaDataExportMojo.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
    
}