
#!groovy

/*
 *
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
pipeline {

    environment {
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
    }

    tools {
        maven 'Maven 3 (latest)'
        jdk 'JDK 1.8 (latest)'
    }


    stages {
        stage('Initialization') {
            steps {
                echo 'Building Branch: ' + env.BRANCH_NAME
                echo 'Using PATH = ' + env.PATH
            }
        }

        stage('Cleanup') {
            steps {
                echo 'Cleaning up the workspace'
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                echo 'Checking out branch ' + env.BRANCH_NAME
                checkout scm
            }
        }

        stage('Build') {
            when {
                expression {
                    env.BRANCH_NAME != 'develop'
                }
            }
            steps {
                echo 'Building'
                sh 'mvn clean install'
            }
            post {
                always {
                    junit(testResults: '**/surefire-reports/*.xml', allowEmptyResults: true)
                    junit(testResults: '**/failsafe-reports/*.xml', allowEmptyResults: true)
                }
            }
        }

        stage('Build develop') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Building'

                // We'll deploy to a relative directory so we can save
                // that and deploy in a later step on a different node
                sh 'mvn clean package'
            }
            post {
                always {
                    junit(testResults: '**/surefire-reports/*.xml', allowEmptyResults: true)
                    junit(testResults: '**/failsafe-reports/*.xml', allowEmptyResults: true)
                }
            }
        }



        stage('Build site') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Building Site'
                sh 'mvn site'
            }
        }

        stage('Stage site') {
            when {
                branch 'develop'
            }
            steps {
                echo 'Staging Site'
                // Build a directory containing the aggregated website.
                sh 'mvn site:stage'
                // Stash the generated site so we can publish it on the 'git-website' node.
                stash includes: 'target/staging/**/*', name: 'reflow-site'
            }
        }
    }

}
