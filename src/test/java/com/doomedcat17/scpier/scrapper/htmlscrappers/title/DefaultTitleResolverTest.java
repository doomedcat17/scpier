package com.doomedcat17.scpier.scrapper.htmlscrappers.title;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultTitleResolverTest {

    private final TitleResolver defaultTitleResolver = TitleResolverProvider.getTitleResolver("eng");

    @Test
    void shouldConsiderItemAsTitle() {
        //given
        String title = "Item: ";
        //then
        assertTrue(defaultTitleResolver.isTitle(title));
    }

    @Test
    void shouldConsiderObjectClassAsTitle() {
        //given
        String title = "Object Class: ";
        //then
        assertTrue(defaultTitleResolver.isTitle(title));
    }

    @Test
    void shouldConsiderContainmentProceduresAsTitle() {
        //given
        String title = "Special Containment Procedures: ";
        //then
        assertTrue(defaultTitleResolver.isTitle(title));
    }

    @Test
    void shouldConsiderDescriptionAsTitle() {
        //given
        String title = "Description: ";
        //then
        assertTrue(defaultTitleResolver.isTitle(title));
    }






}