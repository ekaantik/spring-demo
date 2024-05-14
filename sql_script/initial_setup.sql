-- in case reset
-- DROP SCHEMA public CASCADE;
-- CREATE SCHEMA public;

-- Enter ADMIN as Default User : password is "password"
-- while deploying to generate new password use https://www.browserling.com/tools/bcrypt
insert into user_details (id,phone_number,first_name,last_name,password,type, created_at, updated_at) values
(gen_random_uuid(),'1234567890','Luke','Liu','$2a$10$keJUMq2AVV5E1C5mmki/OeEl0SKWbKzNpK5P5k/Mk15yVSYIkyTsy','ADMIN', CURRENT_TIMESTAMP , CURRENT_TIMESTAMP);
