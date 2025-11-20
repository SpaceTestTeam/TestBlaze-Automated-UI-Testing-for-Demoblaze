# [TestBlaze-Automated-UI-Testing-for-Demoblaze](https://drive.google.com/drive/folders/1eCHLjHFVIVb7aqCkh1tVivoAibe65rAp?usp=sharing)

📂 **Full Project Files:**  
https://drive.google.com/drive/folders/1eCHLjHFVIVb7aqCkh1tVivoAibe65rAp?usp=sharing

---

## Project Idea  

A complete quality assurance and automation testing project for **demoblaze.com**, focusing on verifying functionality, UI/UX consistency, responsiveness, order workflow stability, and overall reliability of the platform.  

---

## 👥 Team Members (pentaRae)

| Name                 | Role                    | GitHub |
|---------------------|--------------------------|--------|
| Walaa Gaber         | Team Leader              | https://github.com/Welagaber |
| Mostafa Elfallal    | QA Automation Member     | https://github.com/Mostafa-elfallal |
| Abdlerhman Waheed   | QA Member                | https://github.com/Abdelrhman-Waheed |
| Aya Yassin          | QA Member                | https://github.com/Ayaa-yassin |
| Amir Helmy          | QA Member                | — |

---

## Instructor  

**Abdelrahman Osama**

---

## Work Plan

### **1. Research & Analysis**
- Platform behavior study  
- User personas and journey mapping  
(Reference: Nielsen Norman UX Research Standards)

### **2. Visual Identity**
- Logo creation  
- Visual assets for documentation

### **3. Main Designs**
- Project poster  
- UI/UX documentation essentials

### **4. Complementary Products**
- SRS  
- Test cases  
- Bug reports  
- Test plans (UI + API)  
- Automation framework

### **5. Review & Finalization**
- Manual review  
- Code review  
- Validation of test artifacts

### **6. Final Presentation**
- Framework demo  
- Reporting and metrics evaluation  

---

## Roles & Responsibilities

### **Team Leader – Walaa Gaber**
- Supervising all QA cycles  
- Managing workflow and documentation  
- Ensuring alignment with software testing standards  

### **Automation Member – Mostafa Elfallal**
- Selenium UI automation development  
- API test implementation (RestAssured)  
- Framework optimization, parallel execution, and TestNG structure  
(Reference: Selenium WebDriver Documentation – https://www.selenium.dev/documentation/)

### **Team Members – Aya, Abdlerhman, Amir**
- Manual testing  
- Preparing test cases  
- Writing bug reports  
- Supporting automation data preparation  

---

## KPIs – Key Performance Indicators

- **Test Coverage Ratio**: Coverage of all site functionalities  
- **Pass/Fail Stability Rate**: Reliability of automated runs  
- **Execution Time Efficiency**: Duration of complete regression suite  
- **Defect Detection Efficiency (DDE)**: Ability to catch issues pre-deployment  
  (Reference: ISTQB Quality Metrics)  
- **Response Time & Element Stability**  
- **Framework Adoption Rate** among QA engineers  

---

## Project Structure

[TestBlaze-Automated-UI-Testing-for-Demoblaze](https://drive.google.com/drive/folders/1eCHLjHFVIVb7aqCkh1tVivoAibe65rAp?usp=sharing)  
├── [Project Planning & Management](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/tree/main/Project%20Planning%20%26%20Management)  
│   ├── [Bug_Report.xlsx](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/Bug_Report.xlsx)  
│   ├── [DemoBlaze_TestCases.xlsx](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/DemoBlaze_TestCases.xlsx)  
│   ├── Project Description.docx  
│   ├── [Project Description.pdf](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/Project%20Description.pdf)  
│   ├── Roles.docx  
│   ├── [Roles.pdf](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/Roles.pdf)  
│   ├── [SRS_Demoblaze_Complete_Final.pdf](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/SRS_Demoblaze_Complete_Final.pdf)  
│   ├── Testing Scope.docx  
│   ├── [Testing Scope.pdf](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/Testing%20Scope.pdf)  
│   └── [API Test Plan.xlsx](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/blob/main/Project%20Planning%20%26%20Management/API%20Test%20Plan.xlsx)  
├── [Automation](https://github.com/SpaceTestTeam/TestBlaze-Automated-UI-Testing-for-Demoblaze/tree/main/Automation)  

```text
├── Automation  
│   ├── pom.xml  
│   ├── src  
│   │   ├── main  
│   │   │   └── java  
│   │   │       └── com  
│   │   │           └── spacetest  
│   │   │               └── demoblaze  
│   │   │                   ├── base  
│   │   │                   │   ├── BaseComponent.java  
│   │   │                   │   ├── BasePage.java  
│   │   │                   │   └── DriverManager.java  
│   │   │                   ├── constants  
│   │   │                   │   └── Constants.java  
│   │   │                   ├── pages  
│   │   │                   │   ├── CartPage.java  
│   │   │                   │   ├── HomePage.java  
│   │   │                   │   ├── LoginPage.java  
│   │   │                   │   ├── ProductPage.java  
│   │   │                   │   ├── SignupPage.java  
│   │   │                   │   └── components  
│   │   │                   │       ├── AboutUsModal.java  
│   │   │                   │       ├── CartItemComponent.java  
│   │   │                   │       ├── CategoryComponent.java  
│   │   │                   │       ├── ContactModal.java  
│   │   │                   │       ├── FooterComponent.java  
│   │   │                   │       ├── NavigationBar.java  
│   │   │                   │       ├── OrderConfirmationModal.java  
│   │   │                   │       ├── PlaceOrderModal.java  
│   │   │                   │       ├── ProductCardComponent.java  
│   │   │                   │       └── ProductSlider.java  
│   │   │                   └── utils  
│   │   └── test  
│   │       ├── java  
│   │       │   └── com  
│   │       │       └── spacetest  
│   │       │           └── demoblaze  
│   │       │               ├── base  
│   │       │               │   ├── BaseTest.java  
│   │       │               │   └── TestDataProviders.java  
│   │       │               └── tests  
│   │       │                   ├── AboutUsModalTest.java  
│   │       │                   ├── CartPageTest.java  
│   │       │                   ├── CategoryMenuTest.java  
│   │       │                   ├── ContactModalTest.java  
│   │       │                   ├── FooterTest.java  
│   │       │                   ├── LoginPageTest.java  
│   │       │                   ├── NavigationBarTest.java  
│   │       │                   ├── PlaceOrderTest.java  
│   │       │                   ├── ProductPageTest.java  
│   │       │                   ├── ProductSliderTest.java  
│   │       │                   ├── ResponsiveLayoutTest.java  
│   │       │                   ├── RestfulBookerFullTests.java  
│   │       │                   └── SignupPageTest.java  
│   │       └── resources  
│   ├── target  
└── README.md  
```

---

## Technologies:  

- Selenium WebDriver  
- TestNG  
- Java  
- Maven  
- RestAssured  
- Allure Reporting  
(References: Selenium, Maven, TestNG official documentation)

--- 

## Project Files  
All documents, automation code, and planning materials are available here:  
https://drive.google.com/drive/folders/1eCHLjHFVIVb7aqCkh1tVivoAibe65rAp?usp=sharing

---

## License  
This project is licensed under the **MIT License**.  
Reference: https://opensource.org/licenses/MIT

---

## Trusted References  
- Selenium Documentation: https://www.selenium.dev/documentation/  
- TestNG Documentation: https://testng.org/doc/  
- Maven Documentation: https://maven.apache.org/  
- OWASP Testing Guide: https://owasp.org/www-project-web-security-testing-guide/  
- ISTQB Foundation Syllabus  
