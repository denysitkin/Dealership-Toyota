CREATE TABLE CarModel
( 
	NameModel            varchar(30)  NOT NULL ,
	PRIMARY KEY  CLUSTERED (NameModel ASC)
)
go

CREATE TABLE Car
( 
	ID_Car               integer  NOT NULL ,
	YearProduction       datetime  NULL ,
	PriceFactory         decimal(10,2)  NULL ,
	NameModel            varchar(30)  NULL ,
	BodyType             varchar(30)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Car ASC),
	 FOREIGN KEY (NameModel) REFERENCES CarModel(NameModel)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE Equipment
( 
	NameEquipment        varchar(50)  NULL ,
	EngineCapacity       varchar(20)  NULL ,
	FuelType             varchar(20)  NULL ,
	Power                varchar(20)  NULL ,
	Transmission         varchar(20)  NULL ,
	InteriorMaterial     varchar(30)  NULL ,
	ID_Car               integer  NOT NULL ,
	PRIMARY KEY  CLUSTERED (ID_Car ASC),
	 FOREIGN KEY (ID_Car) REFERENCES Car(ID_Car)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE Equipment
( 
	NameEquipment        varchar(50)  NULL ,
	EngineCapacity       varchar(20)  NULL ,
	FuelType             varchar(20)  NULL ,
	Power                varchar(20)  NULL ,
	Transmission         varchar(20)  NULL ,
	InteriorMaterial     varchar(30)  NULL ,
	ID_Car               integer  NOT NULL ,
	PRIMARY KEY  CLUSTERED (ID_Car ASC)
)
go

CREATE TABLE Color
( 
	NameColor            varchar(50)  NOT NULL ,
	PRIMARY KEY  CLUSTERED (NameColor ASC)
)
go

CREATE TABLE CarAvailability
( 
	NumOfCars            integer  NULL ,
	ID_Car               integer  NOT NULL ,
	NameColor            varchar(50)  NOT NULL ,
	PriceDealership      decimal(10,2)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Car ASC,NameColor ASC),
	 FOREIGN KEY (ID_Car) REFERENCES Car(ID_Car)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	 FOREIGN KEY (NameColor) REFERENCES Color(NameColor)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE Delivery
( 
	ID_Delivery          integer  NOT NULL ,
	NumOfCars            integer  NULL ,
	TypeDelivery         varchar(20)  NULL ,
	ID_Car               integer  NULL ,
	NameColor            varchar(50)  NULL ,
	DateOrder            datetime  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Delivery ASC),
	 FOREIGN KEY (ID_Car,NameColor) REFERENCES CarAvailability(ID_Car,NameColor)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE Client
( 
	ID_Passport          integer  NULL ,
	Phone                varchar(20)  NULL ,
	ID_Client            integer  NOT NULL ,
	SernameClient        varchar(30)  NULL ,
	NameClient           varchar(30)  NULL ,
	PatronymicClient     varchar(30)  NULL ,
	City                 varchar(20)  NULL ,
	Address              varchar(50)  NULL ,
	PhotoClient          varchar(60)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Client ASC)
)
go

CREATE TABLE Manager
( 
	ID_Manager           integer  NOT NULL ,
	Position             varchar(30)  NULL ,
	Salary               integer  NULL ,
	Phone                varchar(20)  NULL ,
	SernameManager       varchar(30)  NULL ,
	NameManager          varchar(30)  NULL ,
	PatronymicManager    varchar(30)  NULL ,
	PhotoManager         varchar(60)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Manager ASC)
)
go

CREATE TABLE CarSale
( 
	ID_Contract          integer  NOT NULL ,
	DateSale             datetime  NULL ,
	FormPayment          varchar(20)  NULL 
		CHECK  (FormPayment IN ('Карта', 'Наличные', 'Кредит') ),
	ID_Client            integer  NULL ,
	ID_Manager           integer  NULL ,
	ID_Car               integer  NULL ,
	NameColor            varchar(50)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Contract ASC),
	 FOREIGN KEY (ID_Client) REFERENCES Client(ID_Client)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	 FOREIGN KEY (ID_Manager) REFERENCES Manager(ID_Manager)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	 FOREIGN KEY (ID_Car,NameColor) REFERENCES CarAvailability(ID_Car,NameColor)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE DealershipAccounting
( 
	ID_Delivery          integer  NULL ,
	ID_Contract          integer  NULL ,
	ID_Operation         integer  NOT NULL ,
	TypeOperation        varchar(10)  NULL ,
	Sum                  decimal(10,2)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Operation ASC),
	 FOREIGN KEY (ID_Delivery) REFERENCES Delivery(ID_Delivery)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	 FOREIGN KEY (ID_Contract) REFERENCES CarSale(ID_Contract)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE ColorModel
( 
	NameColor            varchar(50)  NOT NULL ,
	ID_Car               integer  NOT NULL ,
	PRIMARY KEY  CLUSTERED (NameColor ASC,ID_Car ASC),
	 FOREIGN KEY (NameColor) REFERENCES Color(NameColor)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION,
	 FOREIGN KEY (ID_Car) REFERENCES Car(ID_Car)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE CarPhoto
( 
	ID_Photo             integer  NOT NULL ,
	PhotoSrc             varchar(60)  NULL ,
	ID_Car               integer  NULL ,
	NameColor            varchar(50)  NULL ,
	PRIMARY KEY  CLUSTERED (ID_Photo ASC),
	 FOREIGN KEY (NameColor,ID_Car) REFERENCES ColorModel(NameColor,ID_Car)
		ON DELETE NO ACTION
		ON UPDATE NO ACTION
)
go

CREATE TABLE Account
( 
	ID_Manager           integer  NOT NULL ,
	NameManager          varchar(30)  NOT NULL ,
	SernameManager       varchar(30)  NOT NULL ,
	Login                varchar(30)  NOT NULL ,
	Password             varchar(30)  NOT NULL ,
	PRIMARY KEY  CLUSTERED (ID_Manager ASC),
	 FOREIGN KEY (ID_Manager) REFERENCES Manager(ID_Manager)
		ON DELETE CASCADE
		ON UPDATE CASCADE
)
go
