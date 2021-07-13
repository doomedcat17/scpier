package com.doomedcat17.scpier.scrapper.pagecontent.html.document;

class IframeProviderTest {
    /*

    @Mock
    private HTMLDocumentProvider htmlDocumentProvider;

    private IframeProvider iframeProvider;

    private final Element testData = TestDataProvider.loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/page-contents.html");


    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        iframeProvider = new IframeProvider(htmlDocumentProvider, new DocumentContentCleanerImpl());
    }

    @Test
    void shouldProvideIframesAudio() throws IOException {
        //given
        Element content = testData.getElementById("shouldProvideIframesContent");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-049");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-049/html/8e52f60fe1f51880be5cf6c40ae5adc7c409c633-3574545131663911736"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-049-audio1.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-049/html/7a1a7bdbfabba5f6eda24ad426225a8ba1119368-16764134901349600626"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-049-audio2.html")
                );

        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-049/html/3c20e3480cc71b180c909c1fb45cd82404efef0d-10430282901693546665"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-049-audio3.html")
                );
        //when
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertEquals(3L, content.children().stream().filter(element -> element.is("audio")).count());
        assertEquals(89, content.childNodeSize());
    }

    @Test
    void shouldProvideIframeImage() throws IOException {
        //given
        Element content = testData.getElementById("shouldProvideImageIframe");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-087");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-087/html/40c580244cd3046f096f5d7e92d05183615180f5-110699635795523362"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-087-image1.html")
                );
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertEquals(4, content.select("img").size());
        assertEquals(29, content.childNodeSize());
    }

    @Test
    void shouldProvideIframeContent() throws IOException {
        //given
        Element content = testData.getElementById("shouldProvideIframeContent");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-139");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-139/html/995a854d7cadae48a0af18803de323f18334cd32-1586188541109417535"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-139-divs.html")
                );
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertNotNull(content.getElementById("firstText"));
        assertNotNull(content.getElementById("firstCollapsible"));
        assertEquals(28, content.childNodeSize());
    }

    @Test
    void shouldProvideIframeContent2() throws IOException {
        //given
        Element content = testData.getElementById("shouldProvideIframeContent2");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-474");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/66cd7b5eeaeaa67270f976b50a83b76529bab540-894703701701610910"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs1.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/05c6a489540c1d80d5364e7f8cf0dba872ea5abd-1256060553409363301"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs2.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/8a9f953896b739abc0a02533257a723923251cd2-5581872531934955789"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs3.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/7176b63aa0a4fc5ed101312da9c001991f8bd0d6-1423361446727052429"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs4.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/ee62c8c8260a5c0951fbc621239d3a262143294e-5097399871964166767"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs5.html")
                );
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-474/html/12531cf8606a8acf4a2ebcee9500c6e85ffa79ca-12796857952071434422"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-474-divs6.html")
                );
        //when
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertEquals(6, content.select("audio-player").size());
    }

    @Test
    void shouldProvideYtVideoContent()  {
        //given
        Element content = testData.getElementById("shouldProvideYtVideoContent");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-506");
        //when
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertEquals(1, content.select("video").size());
        assertTrue(content.selectFirst("video").hasClass("youtube-video"));
    }

    @Test
    void shouldProvideVideoContent() throws IOException {
        //given
        Element content = testData.getElementById("shouldProvideVideoContent");
        PageContent pageContent = new PageContent();
        pageContent.setContent(content);
        pageContent.setSourceUrl("http://www.scpwiki.com/scp-720");
        Mockito.when(htmlDocumentProvider.getWebpageContent("http://www.scpwiki.com/scp-720/html/e33d7d82abfc8fa3ad4fcfd307c0deb32bad66d1-3515188031212119672"))
                .thenReturn(TestDataProvider
                        .loadDocumentFormHTML("src/test/resources/html/testdata/document/iframeProvider-test-data/iframe-contents/scp-720-video.html")
                );
        //when
        iframeProvider.provideIframesContent(pageContent);
        //then
        assertEquals(1, content.select("video").size());
    }

     */
}