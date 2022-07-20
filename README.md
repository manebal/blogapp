Project Name : Blog application
Project description :  Users can post the content online in the Blog application. This application has different APIs related to user, post, audit.
Author name : Balaji Mane
Github username : manbal

--How to run the program
Set the java_home as java path in environment variables
Copy "blog-0.0.1-SNAPSHOT.jar" to temp location
Open the command prompt and go to above location
run below java command
java -jar blog-0.0.1-SNAPSHOT.jar


Application APIs

--view all posts by all users
http://localhost:8080/blog/admin/posts

--view post by post id  
http://localhost:8080/blog/admin/posts/4

--create post on behalf - pass the jsonbody with userId, title, body,loginUser attributes 
http://localhost:8080/blog/admin/users/createpost

--posts of all users
http://localhost:8080/blog/admin/usersposts

--post by particular user (valid values 1 to 10)
http://localhost:8080/blog/admin/usersposts/1

--audit info
http://localhost:8080/blog/admin/audit

--search audit by audit id
http://localhost:8080/blog/admin/audit/{id}

--search audit by userid
http://localhost:8080/blog/admin/audit/user/{userid}

--search audit by post title
http://localhost:8080/blog/admin/audit/posttitle/{postTitle}

--search audit by post body
http://localhost:8080/blog/admin/audit/postbody/{postBody}
 