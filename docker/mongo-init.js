//create solutionDb
db.createCollection('complainUser');
db.complainUser.insert(
    {
        'name' : 'nico',
        'firstName' : 'bod',
        'pseudo' : 'nicow',
        'email' : 'nico.bod@gmail.com',
        'password' : '$2a$10$ZrNev/FCEyfKp3.Zc/irx.OrtFuqL7X6t.tJytIOiYLQ458k2jasO',
        'popularity' : '0',
        'creationDate' : '2020-11-03T11:29:26',
        'role' : 'ADMIN'
    });


