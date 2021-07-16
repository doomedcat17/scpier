# SCPier - SCP Wiki webscrapper
PLEASE KEEP IN MIND IT'S A PROTOTYPE

Scpier is java library for retriving SCPs and Tales directly form SCP Foundation Wiki (all branches) as JSON.

## How it works?
It's basically webscraper. HTML elements are retrived using ```Jsoup```. If pariticular SCP or Tale uses JavaScript than the script is run by ```HtmlUnit```.
Retrived HTML elements are scrapped and interpreted by multiple ```ElementScrapper``` classes. 

To get content from SCP Wiki, you have to initialize ScpFundationDataProvider class:

```
ScpFoundationDataProvider scpFoundationDataProvider = new ScpFoundationDataProvider();
```

Now, let's take a look at data model

## Data model

The main class of the data model is generic ContentNode class. 
It has two variables:
```
ContentNodeType contentNodeType;

T content;
```
```ContentNodeType``` is an enum that defines what type of content particular ```ContentNode``` holds.
And content is content, as simple as that.

```ContentNode``` has some child classes which have additional variables.

### Primary types of ContentNode
#### ContentNodeType.TEXT
Defines ContentNode as TextNode.
The ```content``` is of ```String``` type and it holds a piece of text.<br>
It also has ```styles``` variable. It's a ```Map``` of CSS properties and its values applied to the ```content```.
#### Only local styles are applied!
```
}
    "contentNodeType" : "TEXT",
    "content" : "Bold text",
    "styles" : {
      "font-weight" : "bold"
      }
}
```
Also styles can be empty.
```
}
    "contentNodeType" : "TEXT",
    "content" : "Plain text",
    "styles" : {
      "font-weight" : {}
}
```
#### ContentNodeType.HYPERLINK
Defines ContentNode as HyperlinkNode. It corresponds to the ```<a>``` HTML tag.
It's subclass of TextNode with additonal ```href``` variable of String type. 
```
}
    "contentNodeType" : "HYPERLINK",
      "content" : "practical physician",
      "styles" : {
        "color" : "#901"
      },
      "href" : "http://www.scp-wiki.wikidot.com/death-and-the-doctors-hub"
}
```
#### ContentNodeType.IMAGE, ContentNodeType.VIDEO, ContentNodeType.AUDIO
Defines ContentNode as EmbedNode that holds URL of resource as String.
It also has a variable named ```caption```, which is a ``List`` of TextNodes. It defines a caption (most of images has captions).
Caption can be empty but not null.

ImageNode:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 prior to recovery",
      "styles" : { }
    } ]
}
```
ImageNode with styled caption:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 ",
      "styles" : {
        "color" : "red"
      }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : " prior to recovery",
      "styles" : {
        "text-decoration" : "underline"}
    }]
}
```
ImageNode:
```
{
    "contentNodeType" : "IMAGE",
    "content" : "http://scp-wiki.wdfiles.com/local--files/scp-009/SCP-009.jpg",
    "caption" : [ {
      "contentNodeType" : "TEXT",
      "content" : "SCP-009 prior to recovery",
      "styles" : { }
    } ]
}
```
VideoNode:
```
{
    "contentNodeType" : "VIDEO",
    "content" : "https://www.youtube.com/watch?v=dQw4w9WgXcQ",
    "caption" : []
}
```
AudioNode:
```
{
    "contentNodeType" : "AUDIO",
    "content" : "http://www.scp-wiki.net/local--files/scp-049/Addendum0491.mp3",
    "caption" : []
}
```
### ListNode

ListNode is subclass of ContentNode with List of ContentNodes as content.

#### ContentNodeType.PARAGRAPH
Defines ContentNode ParagraphNode.
It's subclass of ListNode and corresponds to ```<p>``` HTML tag. 
The content is a ```List``` of TextNodes
Example:
```
{
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "This ",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " is ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "one ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "paragraph.",
      "styles" : {
        "text-decoration" : "underline"}
    }]
  }
``` 
#### ContentNodeType.TABLE
It's subclass of TextNode and corresponds to ```<p>``` HTML tag. 
The content is a ```List``` of TextNodes
Example:
```
{
    "contentNodeType" : "PARAGRAPH",
    "content" : [ {
      "contentNodeType" : "TEXT",
      "content" : "This ",
      "styles" : {
        "font-weight" : "bold"
      }
    }, {
      "contentNodeType" : "TEXT",
      "content" : " is ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "one ",
      "styles" : { }
    },
    {
      "contentNodeType" : "TEXT",
      "content" : "paragraph.",
      "styles" : {
        "text-decoration" : "underline"}
    }]
  }
``` 


