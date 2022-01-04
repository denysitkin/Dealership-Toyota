package toyota_app.queries;

public class Query
{
    public static final String verifyLogin = "SELECT Position, A.ID_Manager, A.NameManager, count(1) \n" +
                                             "FROM Account as A INNER JOIN Manager as M ON A.ID_Manager = M.ID_Manager\n" +
                                             "WHERE Login = ? and Password = ?\n" +
                                             "GROUP BY Position, A.ID_Manager, A.NameManager";

    public static final String getCars = "SELECT DISTINCT PhotoSrc, NameModel, BodyType\n" +
                                         "FROM Car as C INNER JOIN CarPhoto as cp ON C.ID_Car = cp.ID_Car\n" +
                                         "WHERE PhotoSrc like '%-white-m.png';";

    public static final String getModelCars = "SELECT C.ID_Car, 'Toyota' as Brand, NameModel, Year(YearProduction) as Year, NameEquipment, NameColor, PriceFactory\n" +
                                              "FROM Car as C, Equipment as E, ColorModel as CM\n" +
                                              "WHERE C.ID_Car = E.ID_Car and C.ID_Car = CM.ID_Car and NameModel = ?";

    public static final String getEquipment = "SELECT distinct CM.ID_Car, E.NameEquipment, E.EngineCapacity, E.FuelType, E.Power, E.Transmission, C.BodyType, E.InteriorMaterial, CP.NameColor, PriceFactory, PhotoSrc\n" +
                                              "FROM Equipment AS E, Car as C, ColorModel as CM, CarPhoto as CP\n" +
                                              "WHERE E.ID_Car = C.ID_Car and CM.ID_Car = C.ID_Car and C.ID_Car = CP.ID_Car and NameEquipment = ? and CP.NameColor = ?;";

    public static final String addCarDealeship = "INSERT INTO CarAvailability (ID_Car, NameColor, NumOfCars, PriceDealership)\n" +
                                             "Values (?, ?, ?, ? * 1.5) ";

    public static final String deliveryCar = "INSERT INTO Delivery (ID_Delivery, NumOfCars, TypeDelivery, ID_Car, NameColor, DateOrder)\n" +
                                             "values (?, ?, ?, ?, ?, ?, ?)";

    public static final String checkCarAvailability = "SELECT count(1) FROM CarAvailability WHERE ID_Car = ? AND NameColor = ?";

    public static final String updateCar = "UPDATE CarAvailability SET NumOfCars = NumOfCars + ? WHERE ID_Car = ? and NameColor = ?;";

    public static final String getDealershipCars = "SELECT DISTINCT NameModel, BodyType\n" +
                                                   "FROM (CarAvailability as CA INNER JOIN Car as C ON C.ID_Car = CA.ID_Car)";

    public static final String getInfoDealeashipCars = "SELECT C.ID_Car, 'Toyota' as Brand, NameModel, Year(YearProduction) as Year, NameEquipment, NameColor, PriceDealership\n" +
                                                       "FROM Car as C, Equipment as E, CarAvailability as CA\n" +
                                                       "WHERE C.ID_Car = E.ID_Car and C.ID_Car = CA.ID_Car and NameModel = ?";

    public static final String getDealershipEquipment = "SELECT distinct CA.ID_Car, E.NameEquipment, E.EngineCapacity, E.FuelType, E.Power, E.Transmission, C.BodyType, E.InteriorMaterial, CA.NameColor, PriceDealership, NumOfCars, NameModel, PhotoSrc\n" +
                                                        "FROM CarAvailability as CA, Equipment as E, Car as C, CarPhoto as CP\n" +
                                                        "WHERE CA.ID_Car = E.ID_Car and CA.ID_Car = C.ID_Car and CP.ID_Car = CA.ID_Car and CP.NameColor = CA.NameColor and NameEquipment = ? and CA.NameColor = ?";

    public static final String updateCarInfo = "UPDATE CarAvailability SET NumOfCars = ?, PriceDealership = ? WHERE ID_Car = ? and NameColor = ?";

    public static final String deleteCarInfo = "DELETE FROM CarAvailability WHERE ID_Car = ? and NameColor = ?";

    public static final String getClients = "SELECT ID_Client, SernameClient, NameClient, PatronymicClient, City, ID_Passport, Phone\n" +
                                            "FROM Client";

    public static final String getClientInfo = "SELECT ID_Client, SernameClient, NameClient, PatronymicClient, ID_Passport, City, Address, Phone, PhotoClient\n" +
                                               "FROM Client\n" +
                                               "WHERE ID_Client = ?";

    public static final String updateClientInfo = "UPDATE Client SET SernameClient = ?, NameClient = ?, PatronymicClient = ?, ID_Passport = ?, City = ?, Address = ?, Phone = ?, PhotoClient = ?  WHERE ID_Client = ?";

    public static final String deleteClient = "DELETE FROM Client WHERE ID_Client = ?";

    public static final String insertClient = "INSERT INTO Client (SernameClient, NameClient, PatronymicClient, City, ID_Passport, Phone, Address, PhotoClient)\n" +
                                              "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String insertSale = "INSERT INTO CarSale (ID_Client, ID_Manager, ID_Car, NameColor, DateSale, FormPayment)\n" +
                                            "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String updateSalary = "UPDATE Manager SET Salary = Salary + (? * 0.01), NumOfSales = NumOfSales + 1 WHERE ID_Manager = ?;";

    public static final String updateNumOfCars = "UPDATE CarAvailability SET NumOfCars = NumOfCars - 1 WHERE ID_Car = ? and NameColor = ?";

    public static final String checkDeleteClient = "SELECT DISTINCT CASE\n" +
                                                    "\tWHEN  CarSale.ID_Client = ? THEN 1\n" +
                                                    "\tELSE 2\n" +
                                                    "\tEND as Num\n" +
                                                    "FROM CarSale, Client \n" +
                                                    "WHERE Client.ID_Client = CarSale.ID_Client";

    public static final String checkDeleteCar = "SELECT DISTINCT CASE\n" +
                                                "WHEN  CarSale.ID_Car = ? THEN 1\n" +
                                                "ELSE 2\n" +
                                                "END as Num\n" +
                                                "FROM CarSale, CarAvailability\n" +
                                                "WHERE CarSale.ID_Car = CarSale.ID_Car";

    public static final String getClientCars = "SELECT DISTINCT ID_Contract, 'Toyota' as Brand, NameModel, Year(YearProduction) as Year, NameEquipment, CS.NameColor, FormPayment\n" +
                                                "FROM Car as C, Equipment as E, CarAvailability as CA, CarSale as CS, Client\n" +
                                                "WHERE C.ID_Car = E.ID_Car and C.ID_Car = CA.ID_Car and CS.ID_Car = CA.ID_Car and CS.ID_Client = Client.ID_Client and CS.ID_Client = ?";

    public static final String getInfoSale = "SELECT CONVERT( VARCHAR, DateSale, 105 ) as DateSale, FormPayment, PriceDealership\n" +
                                             "FROM CarSale INNER JOIN CarAvailability as CA ON CarSale.ID_Car = CA.ID_Car and CarSale.NameColor = CA.NameColor\n" +
                                             "WHERE ID_Client = ? and ID_Contract = ?";

    public static final String getManagers = "SELECT ID_Manager, SernameManager, NameManager, PatronymicManager, Position, Salary, NumOfSales, PhotoManager\n" +
                                             "FROM Manager\n";

    public static String insertManager = "INSERT INTO Manager (SernameManager, NameManager, PatronymicManager, Position, Salary, NumOfSales, Phone, PhotoManager)\n" +
                                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String checkDeleteManager = "SELECT DISTINCT CASE\n" +
                                                    "\tWHEN  CarSale.ID_Manager = ? THEN 1\n" +
                                                    "\tELSE 2\n" +
                                                    "\tEND as Num\n" +
                                                    "FROM CarSale, Manager \n" +
                                                    "WHERE Manager.ID_Manager = CarSale.ID_Manager";

    public static final String deleteManager = "DELETE FROM Manager WHERE ID_Manager = ?";

    public static final String updateManagerInfo = "UPDATE Manager SET SernameManager = ?, NameManager = ?, PatronymicManager = ?, Salary = ?, Position = ?, NumOfSales = ?, Phone = ?, PhotoManager = ?  WHERE ID_Manager = ?";

    public static final String getManagerInfo = "SELECT ID_Manager, SernameManager, NameManager, PatronymicManager, Position, Salary, NumOfSales, Phone, PhotoManager\n" +
                                                "FROM Manager\n" +
                                                "WHERE ID_Manager = ?";

    public static final String getAccounts = "SELECT ID_Manager, SernameManager, NameManager, Login, Password FROM Account";

    public static final String getAccount = "SELECT * FROM Account WHERE ID_Manager = ?";

    public static final String insertAccount = "INSERT INTO Account (ID_Manager, SernameManager, NameManager, Login, Password)\n" +
                                                "VALUES (?, ?, ?, ?, ?);";

    public static final String deleteAccount = "UPDATE Account SET SernameManager = 'Не активный', NameManager = 'Не активный', Login = 'Не активный', Password = 'Не активный' WHERE ID_Manager = ?";

    public static final String getSystemData = "SELECT * FROM SystemData";

    public static final String updateNumCars = "UPDATE SystemData SET NumCars = (Select Sum(NumOfCars) FROM CarAvailability)";

    public static final String updateNumAccounts = "UPDATE SystemData SET NumAccounts = (SELECT count(ID_Manager) FROM Account)";

    public static final String checkBudget = "SELECT Budget - PriceFactory\n" +
                                             "FROM Car, ColorModel, SystemData\n" +
                                             "WHERE ColorModel.ID_Car = ? and NameColor = ? and Car.ID_Car = ColorModel.ID_Car";

    public static final String getNumOfOrder = "SELECT ID_Delivery FROM Delivery";

    public static final String getLastNumOfOrder = "SELECT TOP 1 * FROM Delivery ORDER BY Delivery.ID_Delivery DESC";

    public static final String insertDeliveryInfo = "INSERT INTO Delivery (ID_Car, NameColor, NumOfCars, TypeDelivery, DateOrder)\n" +
                                                    "VALUES (?, ?, ?, ?, ?);\n";

    public static final String updateBudget = "UPDATE SystemData SET Budget = Budget - ?";

    public static final String updateBudgetSale = "UPDATE SystemData SET Budget = Budget + ?";

    public static final String accountOrder = "INSERT INTO DealershipAccounting (ID_Delivery, TypeOperation, Sum, CurrentBudget)\n" +
                                              "VALUES (?, ?, ?, (Select Budget - ? FROM SystemData))";

    public static final String updateAccount = "UPDATE Account SET SernameManager = ?, NameManager = ?, Login = ?, Password = ? WHERE ID_Manager = ?";

    public static final String getAccounting = "SELECT * FROM DealershipAccounting";

    public static final String getLastNumOfSale = "SELECT TOP 1 * FROM CarSale ORDER BY CarSale.ID_Contract DESC";

    public static final String accountSale = "INSERT INTO DealershipAccounting (TypeOperation, Sum, ID_Contract, CurrentBudget)\n" +
                                              "VALUES (?, ?, ?, (Select Budget + ? FROM SystemData))";

    public static final String getBudgetChart = "SELECT ID_Operation, CurrentBudget\n" +
                                                "FROM DealershipAccounting";

    public static final String countSoldCarsChart = "SELECT NameModel, count(*)\n" +
                                                    "FROM CarSale as CS INNER JOIN Car as C ON CS.ID_Car = C.ID_Car\n" +
                                                    "GROUP BY NameModel";
}
