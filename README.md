# `Deploy web-app` on GCP using GitHub Action

## Keypoints

1. This repo creates a docker image of spring boot based backend and react based front end and stores it in Google Container Registry which is later deployed on Cloud Run in GCP.
2. The entire workflow of creating Docker images and deploying them to Cloud Run is automated via Github Actions. You can go through the workflow file in .github/workflows directory.
3. Google Container Registry(GCR) is simply a place to store, manage and secure your docker container images. Further information about GCR can be found [here](https://cloud.google.com/container-registry)
4. Cloud Run is a fully managed serverless platform to develop and deploy highly scalable containerized applications. You can explore further [here](https://cloud.google.com/run)

## Table of Contents

* [Running app locally](#Running-app-locally)
* [Setting up a GCP account](#Setting-up-a-GCP-Account)
* [Creating new GCP Project](#Creating-new-GCP-Project)
* [Creating a Service Account in GCP Project](#creating-a-service-accountsa-and-sa-key-in-gcp-project)
* [Creating Github Secrets](#Creating-github-secrets)
* [Deploying the web-app](#Deploying-the-web-app)
* [Understanding Github Workflow](#Understanding-github-workflow)
* [Frequently Asked Questions](#Frequently-Asked-Questions)

## Running app locally

### Prerequisites to run app locally

* Git - for Version Control. You can download from [here](https://git-scm.com/downloads)
* Java - to run backend service of the app. You can download JDK 8 from [here](https://www.oracle.com/in/java/technologies/javase/javase-jdk8-downloads.html)
* Maven - to manage dependencies required in java code. You can find the maven archived file [here](https://maven.apache.org/download.cgi)
* Node.JS and npm - to run react based frontend service of app. You can download from [here](https://nodejs.org/en/download/)
* MySQL - to be used as database for application. You can download from [here](https://dev.mysql.com/downloads/)

#### You don't need to run this app locally to deploy it on to GCP

Download the above mentioned prerequisites, execute files(if required) and set the environment variables(like $JAVA_HOME for java). Installation of all these tools is well documented and easily available.

Create a database using your mysql shell(downloaded and installed as a part of with MySQL). Below is a screenshot using windows operating system:

![MYSQL SHELL](/assets/mysqlshell.JPG)

Replace the database details in your directory where you have cloned code at visitcount\src\main\resources\application-dev.properties

![Application Properties](/assets/app-properties.JPG)

Run the following commands in Git Bash once the above mentioned prerequisites are all set:

```bash
git clone <Link-to-this-repo>
cd services
mvn clean package -Pdev
java -jar target/visitcount-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

This will start the back-end service. Again open another git bash terminal in the root of the repo you cloned in previous step and run following commands:

```bash
cd ui
npm install
npm run start
```

This will start the front end service and take you to the browser at the localhost:3000 where the front end of app is running.

## Setting up a GCP Account

**You can skip this step if you already have a GCP Project

* Go to [Google Cloud](https://cloud.google.com/) and click on **Get Started for FREE**.
* Login using your gmail account, choose your country, accept terms and conditions and click **Continue**.
* In the next step, fill your details, like account type, Name, Address, credit card details, tax information, etc. If you have old Gmail account and all the information is already there it would take it and you might not have to fill all the details.
* After filling all the details click on **Start my free trial**.
* Google will setup your cloud account and in few seconds your Google Cloud Platform account will be ready to start deploying applications on it. It will look like below:

![Project Setup](/assets/gcp-project-setups-modified.jpg)

## Creating new GCP Project

* Go to [Manage Resources Page](https://console.cloud.google.com/cloud-resource-manager?_ga=2.16905723.313995043.1600681234-1805943322.1596519952) and click on **New Project**
* Fill in the Project Name and keep location as it is. You can also **Edit** Project ID according to availability. Once done click on **Create** and your New GCP Project will be created.

![New Project](/assets/new-project-description-fied.jpg)

* After you have created your project. Go to Billing and click on **Link Billing Account**

![Link Billing](/assets/billing-account-fied.jpg)

* Select Billing Account from dropdown menu and click on **Set Account**

![Set Billing Account](/assets/set-billing-account-modified.jpg)

## Creating a Service Account(SA) and SA Key in GCP Project

* Go to **Navigation Menu(Top left Corner) > IAM & Admin > Service Accounts**
  * Click on **Create Service Account**
  * Under **Service Account Details** provide service account **name** and **description** of your choice and click on **Create**

![Service Account Details](/assets/service-account-details-modified.jpg)
  
* Under **Service Account Permissions** , add following roles one by one and click on **Continue**:
  * Cloud Run Admin
  * Cloud SQL Admin
  * Container Registry Service Agent
  * Service Account User
  * Storage Admin
  * Service Usage Admin

  ![Service Account Permissions](/assets/service-account-permissions-modified.jpg)
  
  * Keep **User Access Section** unchanged and click on **Done**
  * On Service Account Page click on Actions hamburger menu of Service Account you just created and Click on  Manage Keys
  
![Service Account Manage Key](/assets/manage-key.JPG)

  * Click on **Add Key** and then **Create new key**
  
![Service Account Create Key](/assets/create-new-key.JPG)  

* Select **JSON** option and Click on **Create**. A JSON file will get downloaded on your local system, save it we will need it later*

![Service Account JSON](/assets/create-json-key.JPG)

## Creating Github Secrets

* Fork this repository
* Go to **Settings > Secrets** in your forked repository.
* Click on **New Repository Secret** and add Name as **GCP_PROJECT_ID** and value as Project ID of your GCP Project and click on Add Secret

![New Secret](/assets/secret-project.JPG)

* Again click on New Secret and add Name as **SA_KEY** and value as contents of json file that you downloaded and click on Add Secret
* Similarly add following secrets with name and value as below:

| Name          | Value        |
| ------------- | -----------  |
| SA_NAME       | Name of service account that you created in second step. |
| CONNECTION    | Any connection name that you like for your SQL instance. |
| DATABASE_NAME | The name of database that you like to use. |
| DB_USER       | The user name that you like to create to access your database |
| DB_PASSWORD   | Password you like to set for your user | 
  
* KEEP IN MIND, not to change Secret Names otherwise you will need to alter .github/workflows/cloud.yaml file

## Deploying the web app

* The workflow to deploy the application is already set in .github/workflows/cloud.yaml file in your forked repo. The workflow can be triggered manually by visiting actions tab in your forked github repo or automatically whenever some code is pushed.
* Firstly, Visit the Actions tab in your forked repo and Click **I understand my workflows, go ahead & enable them**

![Enable-workflows](/assets/understand-workflows.JPG)

* Open the workflow by clickng on workflow name - **Build and Deploy to Google Cloud Run** in this case

![Open-workflow](/assets/open-workflow.JPG)

* Trigger the workflow manually by visiting actions tab in your github repo and let Github Actions do the magic.

![Run-Workflow](/assets/run-workflow.JPG)
  
* In the Actions tab in your forked repo and open the latest the workflow run and see the progress. You will see something like this when pipeline completes.

![Pipeline-Run](/assets/pipeline-run.JPG)
  
* Once workflow is successful, head towards google cloud console and search cloud run on search bar, you can see the front end and back end services deployed there.

![Cloud Run Services](/assets/cloud-run-services.JPG)

* Click on springboot-app, your backend service, you can check the logs, see the metrics and other details of the service.
* Click on react-app service, your frontend service and you will find the URL of deployed app. Click on URL to see the application.

![React-App-Console](/assets/react-app-console.JPG)

* You will see a front-end like this. Reload to see the counter of visitor changing.

 #### Initial Request can take some time due to initialization

![Front-end](/assets/front-end.JPG)

## Understanding Github Workflow

![Workflow](/assets/supergrads.png)

## Frequently Asked Questions

* _"Container failed to start. Failed to start and then listen on the port defined by the PORT environment variable."_

  * <b>Do you see errors in the logs?</b>  
    Use Cloud Logging to look for application errors in stdout or stderr logs as described in the [Cloud Run logging page](https://cloud.google.com/run/docs/logging). You can also look for crashes captured in Error Reporting, as described in the [Cloud Run error reporting page](https://cloud.google.com/run/docs/error-reporting).  
* _"ERROR: (gcloud.services.enable) User [######@#####.iam.gserviceaccount.com] does not have permission to access projects instance [***] (or it may not exist):"_

  * This error is usually caused because of insufficient roles applied to Service Account. Checkout the roles in [Service Account section](#creating-a-service-accountsa-and-sa-key-in-gcp-project) and see if you have applied all the roles
