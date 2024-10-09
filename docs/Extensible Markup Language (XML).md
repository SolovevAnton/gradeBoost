#basics 
```toc

```

## Theory
**XML Extensible(Расширяемый) Markup Language**  - using tag based syntax. Its called extensible, since you can create own tags

XML used in variety of web technologies:
- **XHTML** - HTML formatted as XML syntax
- **RSS(Real Simple Syndication)/ATOM** - used for publishing, such as blogs
- **AJAX** - asynchronous JavaScript and XML
- **Web services** - using APIs over the web

XML purpose is used to structure and describe information
XML has two ways on handling data - **tags** and **attributes**

### Advantages of XML
- XML keeps content separate from presentation
- XML is open format and can be read by many applications
- XML can be used on both client and the server
- XML has widespread support in multiple languages and runtimes
- Can be used for every system that can works with it

And disadvantages:
- XML is not suitable for very large data sets
- Some other formats might be better in some cases
- Some data types (images for example) are not well presented 
- XML is difficult to read when use for complex data


### XML related technologies
- **XPath** extensible Path Language More [[XPath]]
- **XSLT** XML Stylesheet Language Transformation More [[(XSLT) eXtensible Stylesheet Language Transformation]]
- **XQuery** - more advanced querying language than XPath used for extract info
- **Xpointer, Xlink** - links between and within XML documents

## Types of XML content

There are following types in XML:
![[Types of XML content.png]]

### Document Declaration
it is put in the first line.
It contains:
```XML
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
where
encoding - is encoding used. Default is UTF- 8, but it is still better to state
standalone - indicates if the document is complete by itself, or it needs some other data as well
```
Technically it is optional, but it is recommended to have it

### Tags

**Tags**: are shouted as `<__>`
End tags have `</___>` Start and end tags should match

Tags names can include letters, numbers, periods, hyphens(`-`) and underscores, and must start with a letter or underscore

```XML
<_validTag1>
<other.validTag>
<and-other-valid_Tag>

<1_INVALID-TAG>
<Invalid#2>
<XML> - string XML is reserved
```

There should be some data between tags:
```XML
<artist>The Instructors</artist>
```

Also, there can be empty tags in a form of `<__/>`:
```XML
<invitees /> 
//is the same as
<invitees></invitees>
```

Everything between a tags is content. Content is always a string.
To create nested data put a tag under other tags:

```XML
<color>
	<red>205</red>
	<greaan>123</green>
	<blue>52</blue>
</color>
```

### Attributes

Attributes are Key - Value pairs
- Both values are always Strings.
- Can contain Letters,  Numbers, underscores.
- Always must start with a letter
- appear only once for certain element 

Attributes often used not to store data, but to describe it
Attributes are written in tags
```XML
<fileSize unit="kb">34.6</fileSize>
<cost curr="USD">43.23</cost>
<projectedValue decimals="2" confidence="5">23.45</projectedValue>
```

### Comments
To put comment in code use `<!--` ends with`-->`:

```XML
<textwithattribute attr="atribute"/> 
<!-- 
Comment for text
,..<<>><><>>
-->
<othertext/>
```
They can ==not== go anywhere, except - Inside element brackets  and before the document declaration

### Character data section (CDTATA)
They are part of the document, but not parsed by the XML pars
Defined by:
```XML
<![CDATA[ and ends with]]>
```

They're typically used to contain unescaped text data such as characters that aren't legal within parsed XML. (`& or <>`)
Example:
```XML
<script>
<![CDATA[
	 function f(a,b) {
		 return a < b;
		 }
	]]>
</script>
```

### Processing instructions
Processing instructions are a way for XML content to delivery special instructions to the XML parser
Defined by:
```XML
<?targetName instruction ?>
```

Processing instruction can start with number or letter, then be followed by
- digits
- letters
- hyphens
- periods
- underscores

Example: your app has multi spell checking modes:

```XML
<?SpellCheckMode mode="en-GB" ?>
```

Or to connect your  style css file:
```XML
<?xml-stylesheet type="text/css" href="FirstXMLFile.css" ?>
```

### Entities
**Entities** are intended to shorten and modularize XML documents.
They provide markup for characters that would be otherwise illegal in XML content. 
There are two types:
- **General entities**, which are replaced by the parser with a full string. Examples of this are the copyright and author entities. You can define this to be a string of content that will be replaced by the parser when the XML content is parsed. Example:
```XML
&copyright;
&author;
```
- **Character entities**. For example, if you wanted to include a specific character which is not available in the keyboard in your XML data you can do that by using ampersand(`&`) followed by a hash symbol(`#`) and then the character's code number. example:
```XML
&#060; //is <
&amp; //is &
&quot; //is ""
```

## Validness of XML Documents
Documents in XML are well formed, when all the rules are obeyed, and it is syntaxial correct.
At the top of this, you can add your own set of rules (or example you can supply rules that say things like tag A is only ever allowed to incur inside of tag B and attribute X only has these values and so on), and only when they are passed the document is considered to be valid
![[WellFormaed and Valid XML documents.png]]

There are two ways to do this:
- **Document type definitions(DTDs)**- are simple to use but not very powerful. They're written using as syntax that's somewhat different than XML. It's similar but not exactly the same. More here [[(DTD) Document type definitions in XML|DTD]]
- **XML scheme Definition (XSD)** s much more powerful and flexible then DTDs are. And they're written using XML syntax rules More here [[(XSD) XML scheme Definition|XSD]]


## Namespaces

**Namespaces** are used to prevent tags from different languages from conflicting each other when you try to use them in the same XML document For this, you use namespaces to uniquely identify each tag. 
To use a namespace, just prefix it with a namespace followed by a colon.
```XML
<education:onlineCourse>
```
Why are they needed?
Suppose I have an HTML file with a table in it and HTML tables have things like table rows and table cells. They're used to lay out tabular data. However, if I create an XML document and I work for, say, a furniture company, to me, a table means something completely different. I'd have a table and my table definition for furniture might have a type. It's going to have a price, a material, how many I have in stock. Well if I try to use my XML table inside my HTML table, that's going to cause problems:
```XML
<table>
	<tr>
		<td>Cell Content</td>
	</tr>
	<tr>
		<td>Cell Content</td>
	</tr>
</table>
<!-- THIS IS THE OTHER table in the same doc and there is going to be a problem -->

<table> 
	<type>Coffee</type>
	<price>199.99</price>
	<material>wood</material>
	<stock>25</stock>
</table>
```
so to solve this kind of situation name spaces are used

```XML
<!-- here mane spaces are used
any tag that doesnt have a name space will a prefix in front of it is assumed to be in the default Namespace-->
<html xmlns="http://www.w3.org/1999/xhtml"
	  xmlns:furn="http://www.furniture.org/items">
<table>
	<tr>
		<td>Cell Content</td>
	</tr>
	<tr>
		<td>Cell Content</td>
	</tr>
</table>
<!-- with prefix the problem is completely -->

<furn:table> 
	<furn:type>Coffee</furn:type>
	<furn:price>199.99</furn:price>
	<furn:material>wood</furn:material>
	<furn:stock>25</furn:stock>
</furn:table>
```


## Usage

Rules of XML 
- XML must have single root tag
- XML must be "well-formed"
- Empty tags must me closed with />
- Attribute values must be inside quotes

Example of a weather file from [[JavaScript Object Notation (JSON)|JSON]] :

```XML
<?xml version="1.0" ?>
<dailyForecast>
	<date>2015-06-01</date>
	<description>sunny</description>
	<maxTemp unit="C">22</maxTemp>
	<minTemp unti="C">20</minTemp>
	<windSpeed unit="kmh">12</windSpeed>
	<danger>false</danger>
</dailyForecast>
```

and for nested data:
```XML
<?xml version="1.0" encoding="UTF-8"?>
<forecast>
   <dailyForecast>
      <date>2015-06-01</date>
      <description>sunny</description>
      <maxTemp unit="C">22</maxTemp>
      <minTemp unti="C">20</minTemp>
      <windSpeed unit="kmh">12</windSpeed>
      <danger>false</danger>
   </dailyForecast>
   <dailyForecast>
      <date>2014-06-02</date>
      <description>windy</description>
      <maxTemp unit="C">22</maxTemp>
      <minTemp unti="C">20</minTemp>
      <windSpeed unit="kmh">40</windSpeed>
      <danger>true</danger>
   </dailyForecast>
   <dailyForecast>
      <!-- this represents partly empty element-->
      <date>2014-06-03</date>
      <description />
      <maxTemp unit="C" />
      <minTemp unti="C" />
      <windSpeed unit="kmh" />
      <danger />
   </dailyForecast>
   <dailyForecast />
    <!-- this represents fully empty element-->
</forecast>
```

Space refers to spaces and new lines, and XML also pretty much ignores them when they're either before or after tags, or between tag names and attribute names. However, inside quotation marks, the white space is taken literally
Example:

```XML
<dog     breed="golden      retrivier"> //here string will be saved with all whitespaces it holds
```

## Formatting
Basic formatting rules

- add two to four lines of spacing as an indent for every new level of tags. 
- tags that do not contain other tags can have start and end tags on the same line. 
- Use line breaks if the lines are getting too long. 
- Tags that contain other tags should be on their own line


also use online [formatter](https://www.freeformatter.com/xml-formatter.html)


## Documenting with XML
Use the same table technique as in [[JavaScript Object Notation (JSON)#Documenting JSON|JSON documentation]] .
The only difference is with data types:

There are two kinds of types in XML, *simple* and *complex*. Simple types are just strings whereas complex types contain other tags. 
Although technically all **simple types** are strings, the software that reads the XML may convert that string into a number, Boolean, date, URL and so on so you need to document the type so that developers know what kind of conversion to use. 
This applies to both content and attribute values. 
**Complex types** are elements that contain other elements. For these, list the element name in the type column. If you're documenting the XML file with multiple tables, then have a table for each element type.


> [!NOTE]
> If API use both, XML and JSON, most likely XML attributes will not be used, to make mapping simplier

> [!hint] 
> If XML have only few attributed : add them to the NOTES column
> If there are many attributes add attributes column in the table

Example of request with one table:
```XML
<recordTV>
	<date>2015-06-01</date>
	<time format="24">18:00</time>
	<duration>1.5</duration>
	<channel>54</channel>
</recordTV>
```

The table:
Represents a request to record a television program.

| Element | Description | Type | Required | Notes |
| --- | --- | --- | --- | --- |
| recordTV | Top level | TV program data | Required | |
| &nbsp; &nbsp;date | Date of the program | string | Optional | Format is YYYY-MM-DD HH:MM:SS. Default value is today's date. |
| &nbsp; &nbsp;time | Time the program begins | number | Required | Attributes: **format** has values `24` or `12` for 24 or 12 hour formats. Format is HH:MM, with am or pm afterwards for 12 hour format |
| &nbsp; &nbsp;duration | Length of the program | number | Required | In hours |
| &nbsp; &nbsp;channel | Channel to record | number | Required | |



Example of describing with *multiple tables*:
![[example of XML for several tables.png|400]]

And the tables:
![[tables for multiTable XML examle.png]]



