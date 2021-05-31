# Semestral work on Technical Univercity of Liberec (ALG2) #
## Reservation system ##
System was ctreated to make reservations to Covid-19 test.

### Work assignment ###
Create reservation system which allows user to register(log in) to the system and create reservations to Covid-19 tests.
Program will be read from files and write to files user's information and information about user's reservations. 
Program allows to print all user's reservations to console, and also will send message to user's email address after successfully created reservation.
Program must check all inputs and throw corresponding errors if necessary.

### Solution ###
1. **Use case:**<br/><br/><img width="769" alt="useCase" src="https://user-images.githubusercontent.com/63300936/120177284-8f061700-c208-11eb-950c-67c562f2b7e1.png"><br/><br/>
3. **Class diagram:**<br/><br/><img width="1000" alt="classDiagram" src="https://user-images.githubusercontent.com/63300936/118947644-d44c5e00-b957-11eb-9b1f-83af6624525a.png"><br/><br/>
4. **Data store**<br/>
Program works with JSON files using Jackson library.<br/><br/>
Users file structure:<br/>
```
[
  {
    "email": "user_email",
    "password": "uesr_password",
    "firstName": "user_name",
    "lastName": "user_lastname",
    "age": user_age
  },
  ...
]
```
<br/>Reservations file structure:<br/>
```
[
  {
    "date": "reservation_date",
    "time": "reservation_time",
    "user": {
      "email": "user_email",
      "password": "uesr_password",
      "firstName": "user_name",
      "lastName": "user_lastname",
      "age": user_age
    }
  },
  ...
]
```
<br/><br/>
### Tests ###
1. **Flow**<br/>
![Screenshot 2021-05-20 at 12 12 13](https://user-images.githubusercontent.com/63300936/118962792-09f84380-b966-11eb-914a-b3be86ed8eba.png)<br/>
![Screenshot 2021-05-20 at 12 12 28](https://user-images.githubusercontent.com/63300936/118962798-0bc20700-b966-11eb-9aba-466ad4aa7ace.png)<br/>
![Screenshot 2021-05-20 at 12 12 40](https://user-images.githubusercontent.com/63300936/118962800-0c5a9d80-b966-11eb-8c9c-ff4e680b6a2f.png)<br/><br/>
2. **Email**<br/>
<img width="433" alt="Screenshot 2021-05-20 at 12 28 34" src="https://user-images.githubusercontent.com/63300936/118963666-f6011180-b966-11eb-9b71-e88240baefd9.png"><br/>


### Features: ###
- Easy access from terminal
- Making, saving and printing your reservations
- E-mail sending after reservation is created
- Ability to use your own e-mail templates
- Ability to connect database without rewriting any code
- Ability to use as REST API for web applications

### How to start? ###
1. Clone code from this repository to your computer
2. Install maven dependencies by `mvn install`
3. Run `Main.java` from *src/main/java*
