How to use:

1: Update pom.xml with the following fields.
    - <name> Enter your project name </name>
    - <remote.server> Enter the URL to your tomcat manager/text </remote.server>
    - <db.name> Enter the name of your database </db.name>

2: Update persistence.xml with the following fields.
    - Update JDBC string to your new database for PROD
    - Update JDBC string to your new database for TEST (use prod name and suffix with "_test")

3: Update .github/workflows/mavenworkflow.yml
    - Change database name

4: Add following secrets to your repo
    - REMOTE_USER - Your droplet tomcat user account
    - REMOTE_PW - Your droplet tomcat user account's password

5: Update users in utils/SetupTestUsers.java

6: Clean and build project

7: Run main method in utils/SetupTestUsers.java

8: Enjoy!