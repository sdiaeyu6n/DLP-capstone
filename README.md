## 스마트폰 개인정보 유출 탐지를 위한 Data Loss Prevention 시스템 개발

## 📌 개요  
🔹 **주제**

• 스마트폰의 아웃바운드 네트워크를 통한 개인정보 유출을 탐지하고 실시간 알림을 제공하는 시스템(App) 개발

🔹 **기본 정보**

• 팀 구성: 정보통신공학과 4학년 4명

• 진행기간: 2022. 08 - 2022. 12

• 오픈소스를 활용한 캡스톤디자인프로젝트

🔹 **역할**

• table, relation 설계 진행 - MySQL, DataGrip 사용

• 스마트폰에서 송신한 패킷 데이터와 DB 개인정보 매칭을 위한 정규표현(regex) 패턴 정의

• 결과 보고서 작성 및 발표 자료 제작

## 🖥️ 개발 환경

🔹 **운영체제**

<img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=Windows&logoColor=white"> <img src="https://img.shields.io/badge/linux-FCC624?style=for-the-badge&logo=linux&logoColor=black">

🔹 **개발 도구**  

<img src="https://img.shields.io/badge/Android Studio-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/IntelliJ-000000?style=for-the-badge&logo=IntelliJ IDEA&logoColor=white"> <img src ="https://img.shields.io/badge/-JPA-black?style=for-the-badge"> <img src="https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/DataGrip-000000?style=for-the-badge&logo=DataGrip&logoColor=white"> <img src="https://img.shields.io/badge/Jenkins-D24939?style=for-the-badge&logo=Jenkins&logoColor=white"> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white">

🔹 **개발 언어**  

<img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=Python&logoColor=white"> <img src="https://img.shields.io/badge/C-A8B9CC?style=for-the-badge&logo=C&logoColor=white"> <img src="https://img.shields.io/badge/sql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">


## 📝 프로젝트 내용

🔹 **문제정의**

• 모바일 보안의 중요성이 높아졌지만 보안수칙 실천은 미흡한 상황

• 모바일 개인정보 유출의 가장 큰 문제는 개인정보를 이용한 2차 범죄인 보이스피싱으로 이어진다는 것

🔹 **프로젝트 목적**

• 악성코드 형태의 동작에 초점을 맞추고 있는 기존의 스마트폰 백신 프로그램과 차별적으로 네트워크 모니터링을 통해 개인정보 유출을 탐지하는 시스템을 개발

🔹 **기능 흐름**

1️⃣ 사용자가 PCAP App을 실행하면 App이 백그라운드로 실행되면서 실시간으로 스마트폰의 outbound 네트워크를 패킷 분석용 서버로 전송

2️⃣ 사용자가 사전 정의한 환경에 따라 수신한 패킷을 분류

3️⃣ 패킷 데이터와 DB에 저장된 개인정보 패턴 정규식을 대조하여 일치 여부를 판단

4️⃣ 패킷 데이터가 암호화되지 않은 개인정보일 경우 유출 정보 및 유출 경로를 DB에 저장

5️⃣ 사용자에게 실시간 유출 위험 알림을 SMS로 제공 & 사용자의 기기에서 발생한 유출 위험 발생 로그를 확인할 수 있는 대시보드 제공
