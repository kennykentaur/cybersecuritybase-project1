# Cybersecuritybase - Project 1

For this assignment I've chosen to use the pre-supplied template in Java/Spring (available here https://github.com/cybersecuritybase/cybersecuritybase-project)

My code with the vulnerabilities in can be found at https://github.com/kennykentaur/cybersecuritybase-project1

The code to address the vulnerabilities is in a branch found at https://github.com/kennykentaur/cybersecuritybase-project1/tree/vuln_fixed



I've focused on the following 5 vulnerabilities

* A9-Using Components with Known Vulnerabilities
* A8-Cross-Site Request Forgery (CSRF)
* A5-Security Misconfiguration
* A3-Cross-Site Scripting (XSS)
* A7-Missing Function Level Access Control


**1. Components with Known Vulnerabilities**
   
   These can be identified in different ways.
   The most obvious way is to go through the pom.xml manually and go check ups for each dependency and framework.
   But that time consuming, and you'll most likely miss out on something.
   
   I chose to do it with the command line tool from https://www.owasp.org/index.php/OWASP_Dependency_Check
   
   Steps to reproduce/find the vulnerable components
   1. Download http://dl.bintray.com/jeremy-long/owasp/dependency-check-3.0.2-release.zip
   2. Unzip dependency-check-3.0.2-release.zip
   3. Open a terminal window and change to the directory \dependency-check\bin
   4. Run (assuming windows is being used) # dependency-check.bat --project testing --scan <path\to\downloaded\application>
   5. The previous command generated a html file, containing discovered vulnerabilities, in the directory you where running it from.
   6. In the html file you can see alot of vulnerabilities are from the supplied `cybersecuritybase-project-1.0-SNAPSHOT.jar` that needs to be addressed.

   To fix the vulnerabilities related to Spring Boot, we update in the `pom.xml` from `1.4.2` to `1.5.9`.
   And then we right click on the project in NetBeans and choose "Clean and Build". 
   Then you can re-run the dependency-check.bat with the same parameters as before to validate.
   NOTE! We're not addressing the vulnerabilities that come packaged within cybersecuritybase-project-1.0-SNAPSHOT.jar (Tomcat etcetera)
   
   

**2. Cross-Site Request Forgery (CSRF)**

   The form for filling out name and address is vulnerable to CSRF. 
   
   Steps to reproduce/find
   1. Download the vulnerable codebase from https://github.com/kennykentaur/cybersecuritybase-project1
   2. Load it up in Netbeans, first Build and then Run
   3. Make sure it is up and running on localhost:8080 by opening a browser towards http://localhost:8080/form
   4. Download the html code from https://github.com/kennykentaur/cybersecuritybase-project1/blob/vuln_fixed/test_csrf_web.html and save it locally as a html file
   5. Open the saved html file. The script in the file will now post information into the vulnerable form page
   6. Verify by accessing http://localhost:8080/done and note that you have name: `CSRF` & address: `CSRF` 
   
   The fix for it can be viewed in the code diff at https://github.com/kennykentaur/cybersecuritybase-project1/compare/vuln_fixed?expand=1#diff-f65006aaf8157f0aed7aec7733b529df
   

**3. Security Misconfiguration**
   
   Since there's no security applied to the whole web-tree the H2 Console is accessible to anyone
   
   Steps to reproduce/find
   1. Download the vulnerable codebase from https://github.com/kennykentaur/cybersecuritybase-project1
   2. Load it up in Netbeans, first Build and then Run
   3. Make sure it is up and running, and open a browser towards http://localhost:8080/h2-console/
   4. You should now see the H2 console window
   
   The fix I made was to block access to the console completely. 
   It can be viewed at https://github.com/kennykentaur/cybersecuritybase-project1/compare/vuln_fixed?expand=1#diff-f65006aaf8157f0aed7aec7733b529df
   Under the section for `// Vulnerability 3 - Fix. Block access to admin console." from row 25 and down`.
   
**4. Cross-Site Scripting (XSS)**

   The Thymeleaf th:utext in the /done page allows an attacker to inject javascript code into the /form page.
   This means it's possible to achieve Cross-Site Scripting (XSS).
   
   Steps to reproduce/find
   1. Download the vulnerable codebase from https://github.com/kennykentaur/cybersecuritybase-project1
   2. Load it up in Netbeans, first Build and then Run
   3. Make sure it is up and running by accessing http://localhost:8080/form
   4. In the name field enter `foo`, and in the address field enter `<script>alert('Hi!');</script>`
   5. Click submit
   6. You will now be redirected to the done page which will load the `<script>` and prompt you with an alert-box saying Hi!
   
   This can be addressed by using Thymeleaf's `th:text` that will sanitize the script input into regular html.
   The fix I made can be viewed at https://github.com/kennykentaur/cybersecuritybase-project1/compare/vuln_fixed?expand=1#diff-cfb45e6ffff8af929c672faf0f1419de

   
**5. Missing Function Level Access Control**

   The function to drop all registrations to the event is available at /dropevent
   This function should require that you're logged in, in order to access it to trigger a drop of all event attendees.
   But it is not, leaving it open to any attacker.
   
   Steps to reproduce/find
   1. Download the vulnerable codebase from https://github.com/kennykentaur/cybersecuritybase-project1
   2. Load it up in Netbeans, first Build and then Run
   3. Make sure it is up and running by accessing http://localhost:8080/form
   4. In the name field enter foo, and in the address field enter foo-address
   5. Click submit
   5. You'll now see that foo, with address foo-address, is currently on the event attendinglist when accessing /done
   6. Now access http://localhost:8080/dropevent
   7. This will run the /dropevent function and return you to /done and you'll notice that foo and foo-address is no longer there.
   
   The fix I made was to require you to be logged in, in order to access it. 
   /form and /done is left accessible to anyone who's not authenticated, but accessing /dropevent requires authentication (you can use ted/ted if you like to try it with the fixed version)
   View fix at https://github.com/kennykentaur/cybersecuritybase-project1/compare/vuln_fixed?expand=1#diff-f65006aaf8157f0aed7aec7733b529df
   
   
   
    
