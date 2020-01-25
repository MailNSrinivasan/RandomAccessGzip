# RandomAccessGzip

Random Access Gzip File Approach
As we know, huge data transfer through HTTP is very common. So obviously, this made a necessity of storing huge data and processing it. Data compression techniques have been used in many scenarios to save storage space. But there are few challenges while reading huge compressed files. One important challenge is to read a huge compressed file from an offset. Its very easy to use Random Access File to read a file from an offset, which is the quick and efficient way. But that's not the case with a compressed file. If a compressed file is to be read from an offset, it will throw an error that the file is in bad gzip format. 


In this blog I am going to explain a new approach for randomly accessing a Gzipped file using the techniques of Gzip header flags format and Multiple gzip parts. This random access Gzip file can be used in any compression tools like 7Zip, Winrar, winzip etc..  to uncompress  it, since the format of the Gzip file is NOT altered. 

Multiple gzip parts :

The gzip file have a format like below

     <HEADER><COMPRESSED DATA><FOOTER>
    <H><CD><F>

A Single compressed file can have the Header, Compressed Data and Footer sequence as many as they can, like below

      <H><CD><F><H><CD><F><H><CD><F><H><CD><F><H><CD><F>..................

This multiple sequence of this format is helpful to read from any header in between the file.
 
Gzip header flags format:
Secondly the Header has a provision to include comments as per RFC 1952 GZIP File Format. In this link, its been explained about the format of Header :

Header format:

Each Header has the following structure:
+-----+------+------+------+------+------+------+------+------+------+
|ID1|ID2|CM |FLG|     MTIME                     |XFL|OS | (more-->)
+-----+------+------+------+------+------+------+------+------+------+
     1      2          3       4        5        6        7        8        9       10

 
The 4th Byte of the Header represents the flag information and as below
FLG (FLaGs)
This flag byte is divided into individual bits as follows:
bit 0   FTEXT
bit 1   FHCRC
bit 2   FEXTRA
bit 3   FNAME
bit 4   FCOMMENT
bit 5   reserved
bit 6   reserved
bit 7   reserved
 
 

The highlighted above bit is set then it means a zero-terminated file comment is present.
