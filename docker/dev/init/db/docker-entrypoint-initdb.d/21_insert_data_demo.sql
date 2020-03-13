-- Remplissage base de données--

-- 1/user (1-?)-- ->id1 -> responsable

INSERT INTO complain.complainuser
    (name, firstname, email, password, creationdate, popularity)
    VALUES
    ('nico', 'bod', 'test@test.com', 'mdp', '2016-06-22 19:10:25', 0);

-- 2/theme (1-?)-- ->id1 -> corona virus

INSERT INTO complain.theme
    (complainuser_id, name, creationdate, popularity, nbrrequest)
    VALUES
    (1, 'corona virus', '2020-03-13 12:11:12', 0, 1);

-- 3/request (1-?)-- ->id1 -> fermeture écoles request

INSERT INTO complain.complainrequest
    (request, complainuser_id, creationdate, popularity, nbrresponse, theme_id)
    VALUES
    ('Fermeture écoles', 1, '2020-03-13 12:14:12', 0 , 1 , 1);

-- 4/ response (1-?)-- ->id1 -> fermeture ecoles response

INSERT INTO complain.complainresponse
    (response, creationdate, popularity, complainuser_id, complainrequest_id)
    VALUES
    ('pour limiter la propagation, les enfants ne ramènerons pas le virus à la maison', '2020-03-13 12:17:12', 1, 1, 1 );