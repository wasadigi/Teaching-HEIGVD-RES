# Lecture 7: The HyperText Transfer Protocol, Part 2

## Table of Contents

* [Objectives](#Objectives)
* [Preparing for the Test](#PreparingForTheTest)
* [Lecture](#Lecture)
   * 1 - [Caching](#Caching)
   * 2 - [Authentication](#Authentication)



## <a name="Objectives"></a>Objectives

The objective of this lecture is to examine two aspects of the HTTP protocol specification. The first one is **caching**, the second one is **authentication**. We will look at these two aspects by studying what the RFCs are specifying and by looking at some very good references available on the web. The objective of this lecture is also for you to **prepare a comprehensive and well structured answer** to a question that you will be asked in the upcoming written test.

## <a name="PreparingForTheTest"></a>Preparing for the Test

In the upcoming test, you will be asked on the two following questions (you will have the possibility to choose one of the two):


1. Explain the difference between the cache expiration and validation models defined in the HTTP specification.

2. Explain the difference between the basic and digest authentication schemes defined in the HTTP specification.

You should prepare your answers by using these lecture notes **as a starting point**. But you are expected to do more reading, both in the RFC and in other documents. You will be expected to provide a very well structured answer, with technical details, with sequence diagrams, etc. You will be evaluated on the following points:

* is the answer well structured? Is there an introduction that clearly sets the context and defines the problem? Is the information presented in a logical and easy to understand manner? 

* is the answer detailed and complete? Is there precise information about the headers sent by clients and servers (syntax and semantics)? Are there sequence diagrams that make it easy to understand how these headers work in practice?

* is the answer accurate and correct?

**Important note: ** if you decide to focus on caching, you should still be able to answer shorter questions about security based on this page content. Likewise, if you decide to focus on security, you should be able to answer questions about caching based on this page content. 



## <a name="Lecture"></a>Lecture


### <a name="Caching"></a>1. Caching

The introduction of [RFC 2616](http://tools.ietf.org/html/rfc2616) mentions **caching** as one of the key elements of the HTTP 1.1 specification. In [Section 1.4](http://tools.ietf.org/html/rfc2616#section-1.4), the authors explain that the interaction between an HTTP client and an HTTP server often goes through a number of **intermediaries** (proxies, gateways, tunnels). We will look more closely at proxies when we look at HTTP from an infrastructure point of view, but we can already mention that **caching** is often done at various points in the HTTP processing pipeline. Two reasons for introducing caching are **performance** and **bandwidth usage**.

We have already discussed **a related notion** earlier in the course, when we have looked at **buffered IOs**. You might remember the analogy of the person going to the store and buying either one bottle or a whole case. **Caching** is a bit different and introduces the question of reusing data over time. To illustrate this idea, let us consider two examples:

* Think about what is happening when all students arrive at school in the morning. Many of them might open their laptop and visit the same [popular news site](http://radar.oreilly.com). Without caching, every browser would send an HTTP request to the origin server, get HTML and linked media content and render a page. It would work, but it would mean crossing the Internet over and over and transferring quite a bit of data over the wire. Not optimal from a performance and bandwidth consumption point of view. With caching, **the first student would trigger the download of content, which would be cached by the forward proxy installed at the school**. The requests issued by all other students would go through the forward proxy, which would say: *"Hold on: I have seen this request *recently* and I still have a copy of the answer; no need to traverse the Internet, I can answer directly!"*

* Think about what is happening **on the other side of the chain**, close to the origin server. The web site dynamically generates content. Without caching, the server would need to execute logic (consuming CPU and time) and fetch data in the database (consuming CPU, bandwidth and time) over and over again. **Even with moderate traffic, this would lead to very poor performance**. **With caching**, the server would generate dynamic content and keep in a cache *for some time*. When receiving requests from clients, it would ask the following question: *"Did I already compute this **recently** and do I already have **ready-to use** content for the client?* **The positive impact on performance will be dramatic**.

These two examples suggest that **caching does happen at several places along the HTTP processing pipeline**. Browsers manage a cache, forward proxies manage a cache, reverse proxies manage a cache, applications manage a cache, databases manage a cache. **All of these caches contribute to resource savings and improved performance.**

**Unfortunately, using a cache can raise tricky questions** (and the situation is worse in distributed infrastructures which are typically used for high-traffic applications). To make a long story short, the question is whether the information that is available in the cache is **fresh enough** and is **still accurate**. To come back to the two previous examples, consider the following points:

* if the content was kept in the forward proxy cache *forever*, then the first student would arrive on a Tuesday morning, get the content of the news site for that day. All other students would then see the same content. For Tuesday, that would work like a charm. But on Wednesday morning, everybody would still see Tuesday's content… same thing for the next 8739 days… 

* On the other side of the processing chain, the server would generate content based on the current database state. It would keep a copy of the content of the cache. Later on, as content producers would alter the state of the database (by using their favorite content-management tool), the same old content would be served to clients.

To cope with this issue, the HTTP specification defines two complementary models: an **expiration model** and a **validation model**. The expiration model is defined in [Section 13.2](http://tools.ietf.org/html/rfc2616#section-13.2) and the validation model is defined in [Section 13.3](http://tools.ietf.org/html/rfc2616#section-13.3). Simply stated, the expiration model allows the server to indicate how long the generated content is expected to be valid (i.e. the server says: *"No need to get back to me before that date."*). The validation model allows the client to inform the server that it already has already received content in the past, which *might* still be valid (up to the server to decide if it needs to generate a fresh version or if the previous one is still valid).

While we will not cover all the details of how the expiration and validation models are implemented, in other words how specific headers are used by clients and servers, you have to be able to explain them. It is your job to study these details, both by reading the relevant RFC sections and by reading one of these excellent descriptions: [this one](http://tomayko.com/writings/things-caches-do), [that one](http://www.mobify.com/blog/beginners-guide-to-http-cache-headers/) and [that one](http://www.mnot.net/cache_docs/).


### <a name="Authentication"></a>2. Authentication

The second topic is related to security and more specifically to authentication. The question to answer is how can the server verify the identity of the user issuing an HTTP request. As it is the case for other aspects of the HTTP specification, the authors have defined a **framework** that makes it possible to plug additional authentication schemes (on top of the two documented in [RFC 2617](https://tools.ietf.org/html/rfc2617), entitled *HTTP Authentication: Basic and Digest Access Authentication*. For instance, Amazon has defined its [own authentication scheme](http://docs.aws.amazon.com/AmazonS3/latest/dev/RESTAuthentication.html#ConstructingTheAuthenticationHeader).

[Section 1.2](https://tools.ietf.org/html/rfc2617#section-1.2) of RFC 2617 describes the HTTP authentication framework in the following terms:

```
   HTTP provides a simple challenge-response authentication mechanism
   that MAY be used by a server to challenge a client request and by a
   client to provide authentication information. It uses an extensible,
   case-insensitive token to identify the authentication scheme,
   followed by a comma-separated list of attribute-value pairs which
   carry the parameters necessary for achieving authentication via that
   scheme.

      auth-scheme    = token
      auth-param     = token "=" ( token | quoted-string )

   The 401 (Unauthorized) response message is used by an origin server
   to challenge the authorization of a user agent. This response MUST
   include a WWW-Authenticate header field containing at least one
   challenge applicable to the requested resource. The 407 (Proxy
   Authentication Required) response message is used by a proxy to
   challenge the authorization of a client and MUST include a Proxy-
   Authenticate header field containing at least one challenge
   applicable to the proxy for the requested resource.

      challenge   = auth-scheme 1*SP 1#auth-param
```

Two schemes are defined in the specification:

* the first one is the [Basic Authentication Scheme](https://tools.ietf.org/html/rfc2617#section-2), which results in the **user id and password to be sent in clear** in the `Authorization` header. Needless to say, this scheme should not be used, unless a transport channel is used (HTTPS).

* The second one is the [Digest Access Authentication Scheme](https://tools.ietf.org/html/rfc2617#section-2). Let us start by looking at what the specification tells us about the objectives of the scheme:

```
   The protocol referred to as "HTTP/1.0" includes the specification for
   a Basic Access Authentication scheme[1]. That scheme is not
   considered to be a secure method of user authentication, as the user
   name and password are passed over the network in an unencrypted form.
   This section provides the specification for a scheme that does not
   send the password in cleartext,  referred to as "Digest Access
   Authentication".

   The Digest Access Authentication scheme is not intended to be a
   complete answer to the need for security in the World Wide Web. This
   scheme provides no encryption of message content. The intent is
   simply to create an access authentication method that avoids the most
   serious flaws of Basic authentication.
```

The main difference between the two schemes is then described the following terms:

```
   Like Basic Access Authentication, the Digest scheme is based on a
   simple challenge-response paradigm. The Digest scheme challenges
   using a nonce value. A valid response contains a checksum (by
   default, the MD5 checksum) of the username, the password, the given
   nonce value, the HTTP method, and the requested URI. In this way, the
   password is never sent in the clear. Just as with the Basic scheme,
   the username and password must be prearranged in some fashion not
   addressed by this document.
```

The details of the authentication scheme are actually a bit more complex than this statement, but there are two elements that we can already note: 1) instead of sending credentials in clear, the client is sending a hashed value and 2) what is hashed is a combination of the user's password and of a dynamic value generated by the server.

When the server receives a request to access a protected resource, it has to send a `WWW-Authenticate` response header, as described in [Section 3.2.1](https://tools.ietf.org/html/rfc2617#section-3.2.1):

```
   If a server receives a request for an access-protected object, and an
   acceptable Authorization header is not sent, the server responds with
   a "401 Unauthorized" status code, and a WWW-Authenticate header as
   per the framework defined above, which for the digest scheme is
   utilized as follows:

      challenge        =  "Digest" digest-challenge

      digest-challenge  = 1#( realm | [ domain ] | nonce |
                          [ opaque ] |[ stale ] | [ algorithm ] |
                          [ qop-options ] | [auth-param] )


      domain            = "domain" "=" <"> URI ( 1*SP URI ) <">
      URI               = absoluteURI | abs_path
      nonce             = "nonce" "=" nonce-value
      nonce-value       = quoted-string
      opaque            = "opaque" "=" quoted-string
      stale             = "stale" "=" ( "true" | "false" )
      algorithm         = "algorithm" "=" ( "MD5" | "MD5-sess" |
                           token )
      qop-options       = "qop" "=" <"> 1#qop-value <">
      qop-value         = "auth" | "auth-int" | token
```

All items in the digest challenge are fully described in the RFC, which you should read even if we do not reproduce it fully here.

The client should then send an `Authorization` request header, as described [Section 3.2.2](https://tools.ietf.org/html/rfc2617#section-3.2.2):

```
   The client is expected to retry the request, passing an Authorization
   header line, which is defined according to the framework above,
   utilized as follows.

       credentials      = "Digest" digest-response
       digest-response  = 1#( username | realm | nonce | digest-uri
                       | response | [ algorithm ] | [cnonce] |
                       [opaque] | [message-qop] |
                           [nonce-count]  | [auth-param] )

       username         = "username" "=" username-value
       username-value   = quoted-string
       digest-uri       = "uri" "=" digest-uri-value
       digest-uri-value = request-uri   ; As specified by HTTP/1.1
       message-qop      = "qop" "=" qop-value
       cnonce           = "cnonce" "=" cnonce-value
       cnonce-value     = nonce-value
       nonce-count      = "nc" "=" nc-value
       nc-value         = 8LHEX
       response         = "response" "=" request-digest
       request-digest = <"> 32LHEX <">
       LHEX             =  "0" | "1" | "2" | "3" |
                           "4" | "5" | "6" | "7" |
                           "8" | "9" | "a" | "b" |
                           "c" | "d" | "e" | "f"

   The values of the opaque and algorithm fields must be those supplied
   in the WWW-Authenticate response header for the entity being
   requested.
```

Again, you should study the rest of this section in the RFC to make sure that you understand the role of the different fields, including the `cnonce` and `nc-value` fields. One key field is the `request-digest`, which has to be computed by the client. The algorithm for doing this computation is described in Sections [3.2.1.1](https://tools.ietf.org/html/rfc2617#section-3.2.1.1), [3.2.1.2](https://tools.ietf.org/html/rfc2617#section-3.2.1.2) and [3.2.1.3]([3.2.1.1](https://tools.ietf.org/html/rfc2617#section-3.2.1.1). An example is provided in [Section 3.5](https://tools.ietf.org/html/rfc2617#section-3.5).

To see how this can be implemented in practice, you should have a look at this [article](http://usamadar.com/2012/06/11/implementing-http-digest-authentication-in-java/), which has a link to a [sample implementation](https://gist.github.com/usamadar/2912088) in java. You can also have a look at [this Node.js module source code](https://github.com/gevorg/http-auth/blob/master/lib/auth/digest.coffee). The [wikipedia entry](http://en.wikipedia.org/wiki/Digest_access_authentication) also contains examples and is a great way to see how the digest is computed.

## <a name="Resources"></a>Resources</a>


* These articles about caching:
  * [A Beginner's Guide to HTTP Cache Headers](http://www.mobify.com/blog/beginners-guide-to-http-cache-headers/)
  * [Things Caches Do](http://tomayko.com/writings/things-caches-do)
  * [Caching Tutorial](http://www.mnot.net/cache_docs/)
  
* These resources about authentication:
  * [The wikipedia entry about digest access authentication](http://en.wikipedia.org/wiki/Digest_access_authentication)
  * A [nice article about digest access authentication](http://www.sitepoint.com/understanding-http-digest-access-authentication/).
  

