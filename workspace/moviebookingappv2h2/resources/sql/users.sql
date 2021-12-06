-- :name create-user! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email, pass)
VALUES (:id, :first_name, :last_name, :email, :pass)


-- :name create-admin! :! :n
-- :doc creates a new user record
INSERT INTO users
(id, first_name, last_name, email,admin, pass)
VALUES (:id, :first_name, :last_name, :email,true, :pass)

-- :name update-user! :! :n
-- :doc updates an existing user record
UPDATE users
SET first_name = :first_name, last_name = :last_name, email = :email
WHERE id = :id

-- :name get-user :? :1
-- :doc retrieves a user record given the id
SELECT * FROM users
WHERE id = :id

-- :name get-user-by-email-and-pass :? :1
-- :doc retrieves a user record given the email
SELECT id,first_name , last_name ,email,admin FROM users
WHERE email = :email and pass =:pass

-- :name delete-user! :! :n
-- :doc deletes a user record given the id
DELETE FROM users
WHERE id = :id
