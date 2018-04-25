drop table Students;
drop table Universities;
drop table StudentLoginInfo;

create table Students (
    FirstName               VARCHAR(20),
    LastName                VARCHAR(30),
    UserID                  VARCHAR(20),
    Password                VARCHAR(30),
    Email                   VARCHAR(30),
    CellNumber              VARCHAR(10),
    SecurityQuestion        VARCHAR(50),
    SecurityAnswer          VARCHAR(50)
);

create table StudentLoginInfo (
    UserID                  VARCHAR(20),
    Password                VARCHAR(30)
);

create table Universities (
    UName                   VARCHAR(30),
    Location                VARCHAR(50),
    AcceptanceRate          VARCHAR(4),
    TotalEnrollment         INT,
    Cost                    INT
);
