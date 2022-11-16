-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 31, 2022 at 12:52 PM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

-- SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
-- START TRANSACTION;
-- SET time_zone = "+00:00";


-- /*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
-- /*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
-- /*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
-- /*!40101 SET NAMES utf8mb4 */;

--
-- Database: `Student_Registration_System`
--

-- --------------------------------------------------------

--
-- Table structure for table `courses`
--

CREATE TABLE `courses` (
  `courseID` varchar(255) NOT NULL PRIMARY KEY,
  `courseName` varchar(255) NOT NULL,
  `courseDuration` int(1) NOT NULL,
  `costPrice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- Dumping data for table `courses`


INSERT INTO `courses` (`courseID`, `courseName`,`courseDuration`,`costPrice`) VALUES
('SOEN','Software Engineering', 4, 23500.00),
('ACMP','Applied Computer Science', 4, 20500.00),
('JOURN','Journalism', 4, 31000.00),
('BIT', 'Information Technology', 4, 23500.00),
('COMPSCI','Computer Science', 4, 28500.00),
('BINM', 'Information Management', 4, 23500.00),
('MED', 'Medicine And Surgery', 4, 20500.00),
('BIS', 'Information Science', 4, 20500.00);

-- --------------------------------------------------------

--
-- Table structure for table `courseadvisor`
--

CREATE TABLE `courseadvisor` (
  `ID` varchar(150) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) NOT NULL,
  `Password` varchar(150) NOT NULL DEFAULT 'password',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `courseadvisor`
--

INSERT INTO `courseadvisor` (`ID`,`Name`, `Email`,`Password`) VALUES
('CAD-001','Md. Joy Muthoni','cad001@kisiiuniversity.ac.ke','CAD-001'),
('CAD-013','Mr. Alex Nyandego','cad013@kisiiuniversity.ac.ke','CAD-013'),
('CAD-054','Dr. Alfred Mutua','cad054@kisiiuniversity.ac.ke','CAD-054'),
('CAD-106','DR.Islam Shamsudin','cad106@kisiiuniversity.ac.ke','CAD-106');


-- --------------------------------------------------------
--
-- Table structure for table `admissions`
--

CREATE TABLE `admissions` (
  `ID` varchar(150) NOT NULL,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) NULL,
  `Password` varchar(64) DEFAULT 'password',
  PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `admissions` VALUES ('ADM-001','Brian Mugo','adm-001@kisiiuniversity.ac.ke','ADM-001'),
                                ('ADM-013','Tanjina Helaly','adm-013@kisiiuniversity.ac.ke','ADM-013');


-- --------------------------------------------------------
--
-- Table structure for table `registrar`
--

CREATE TABLE `registrar` (
  `ID` VARCHAR(150) NOT NULL PRIMARY KEY,
  `Name` varchar(100) NOT NULL,
  `Email` varchar(64) NULL,
  `Password` varchar(64) DEFAULT 'password'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `registrar` VALUES ('REG-001','Brian Mugo','reg-001@kisiiuniversity.ac.ke','REG-001'),
                               ('REG-013','Tanjina Helaly','reg-013@kisiiuniversity.ac.ke','REG-013');


-- --------------------------------------------------------
--
-- Table structure for table `enrollment`
--

CREATE TABLE `enrollment` (
  `ID` VARCHAR(100) NOT NULL PRIMARY KEY,
  `Name` varchar(64) NOT NULL,
  `Email` varchar(64) NULL,
  `Password` varchar(64) DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `enrollment` VALUES ('ENR-001','Brian Mugo','enr-001@kisiiuniversity.ac.ke','ENR-001'),
                                ('ENR-013','Tanjina Helaly','enr-013@kisiiuniversity.ac.ke','ENR-013');



-- --------------------------------------------------------
--
-- Table structure for table `schooldept`
--

CREATE TABLE `schooldept` (
  `ID` VARCHAR(20) NOT NULL PRIMARY KEY,
  `Name` varchar(64) NOT NULL,
  `Email` varchar(64) NULL,
  `Password` varchar(64) DEFAULT 'password'
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 

INSERT INTO `schooldept` VALUES ('SCH-001','Brian Mugo','sch-001@kisiiuniversity.ac.ke','SCH-001'),
                                ('SCH-013','Tanjina Helaly','sch-013@kisiiuniversity.ac.ke','SCH-013');




-- --------------------------------------------------------
--
-- Table structure for table `bursar`
--
CREATE TABLE `bursar` (
  `ID` VARCHAR(20) NOT NULL PRIMARY KEY,
  `Name` varchar(64) NOT NULL,
  `Email` varchar(64) NULL,
  `Password` varchar(64) DEFAULT 'password'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `bursar` VALUES ('BURS-001','Brian Mugo','burs-001@kisiiuniversity.ac.ke','SCH-001'),
                                ('BURS-013','Tanjina Helaly','burs-013@kisiiuniversity.ac.ke','SCH-013');
-- --------------------------------------------------------

--
-- Table structure for table `courseunits`
--

CREATE TABLE `courseunits` (
  `courseID` varchar(255) NOT NULL,
  `unitCode` varchar(255) NOT NULL PRIMARY KEY,
  `unitName` varchar(255) NOT NULL,
  `unitDesc` varchar(255) NOT NULL,
  FOREIGN KEY (courseID) REFERENCES courses(courseID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `coursenits`
--

INSERT INTO `courseunits` VALUES
('ACMP', 'ACMP000', 'Introductory Electronics', 'Physical concepts of Electronics as used in computing.'),
('COMPSCI', 'CS000', 'Data  Structures and Algorithms.', 'Through Efficient Data structuring and algorithm consumption  Systems of high quality interms of perfomance can  be developed.'),
('JOURN', 'JOURN000', 'Interview Criteria and Best practices. ', 'The Unit describes the various ways in which interviews should be carried out for betterment of user Experience.\r\n '),
('SOEN', 'SOEN000', 'Software Standards', 'Unit deals with the standards set to a good software  development method')
('ACMP', 'ACMP212', 'Calculus I', 'Learn the fundamentals of Calculus such as Intergration and differntiation.'),
('ACMP', 'ACMP111', 'Fundamentals Of Applied Computing', 'Get to understand what applied commputing really is'),
('ACMP', 'ACMP205', 'API Design & Implementation',  'You will learn types of API Design and create a RESTful interface API'),
('ACMP', 'ACMP112', 'HIV/AIDS', 'Understand the difference between HIV and AIDS, contraction methods and prevention.'),
('COMPSCI', 'CS176', 'Calculus I', 'Learn the fundamentals of Calculus such as Intergration and differntiation.'),
('COMPSCI', 'CS103', 'Fundamentals Of Computer Science', 'Get to understand what applied commputing really is'),
('COMPSCI', 'CS202', 'API Design & Implementation',  'You will learn types of API Design and create a RESTful interface API'),
('COMPSCI', 'CS112', 'HIV/AIDS', 'Understand the difference between HIV and AIDS, contraction methods and prevention.'),
('JOURN', 'JOURN131', 'Broadcast Journalism', 'Understand what is broadcast journalism, and how it works.'),
('SOEN', 'SOEN111', 'Calculus I', 'Learn the fundamentals of Calculus such as Intergration and differntiation.'),
('SOEN', 'SOEN105', 'Fundamentals Of Software Engineering', 'Get to understand what software engineering really is'),
('SOEN', 'SOEN255', 'API Design & Implementation',  'You will learn types of API Design and create a RESTful interface API'),
('SOEN', 'SOEN112', 'HIV/AIDS', 'Understand the difference between HIV and AIDS, contraction methods and prevention.');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `students` (
  `Std_ID` varchar(255) NOT NULL,
  `Std_Name` varchar(255) NOT NULL,
  `Std_Email` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Course` varchar(255) NOT NULL,
  `YOS` tinyint(4) NOT NULL,
  `Status` varchar(255) NOT NULL,
  PRIMARY KEY (`Std_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Student`
--

INSERT INTO `students` (`Std_ID`, `Std_Name`, `Std_Email`, `Password`, `Course`, `YOS`, `Status`) VALUES
('IN16/00010', 'CALEB SAGINI', 'calebm@gmail.com', 'IN16/00010', 'SOEN', 1, 'active'),
('IN16/00011', 'ANIS ISMAEL', 'ismaelm@gmail.com', 'IN16/00011', 'SOEN', 2, 'dropped-out'),
('IN16/00012', 'OLESAPIT GUNNER', 'ole@gmail.com', 'IN16/00012', 'ACMP', 1, 'dropped-out'),
('IN16/00013', 'MOHAMMED HISHAM', 'hisham@gmail.com', 'IN16/00013', 'JOURNALISM', 2, 'active'),
('IN16/0003', 'BEZYL MOPHAT OTIENO', 'bezylmophatotieno@gmail.com', 'IN16/0003', 'SOEN', 1, 'dropped-out'),
('IN16/0004', 'JOHN DOE', 'johndoe@gmail.com', 'IN16/0004', 'ACMP', 2, 'dropped-out'),
('IN16/0005', 'Michael Jordan Oreo', 'michaeljordan@gmail.com', 'IN16/0005', 'CS', 3, 'active'),
('IN16/0006', 'Mitchel Obama', 'obama@gmail.com', 'IN16/0006', 'journalism', 4, 'active'),
('IN16/0007', 'MOHAMMED UMAR', 'umarm@gmail.com', 'IN16/000', 'SOEN', 2, 'inactive'),
('IN16/0008', 'MOHAMMED HISHAM', 'hisham@gmail.com', 'IN16/0008', 'JOURNALISM', 2, 'active'),
('IN16/0009', 'OLESAPIT GUNNER', 'ole@gmail.com', 'IN16/0009', 'ACMP', 1, 'inactive'),
('IN17/0001', 'JOSHUA DEEN', 'deen@gmail.com', 'IN17/0001', 'BIT', 4, 'active');


-- --------------------------------------------------------

--
-- Table structure for table `Unit_Results`
--

-- Kindly ensure that you enforce a relationship between student table and this table and 
-- courseunits table and this table

CREATE TABLE `unitresults` (
  `unitCode` varchar(255) NOT NULL,
  `Std_ID` varchar(255) NOT NULL,
  `GPA` decimal(2,1) NOT NULL,
  FOREIGN KEY (Std_ID) REFERENCES students(Std_ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Unit_Results`
--

INSERT INTO `unitresults` VALUES ('ACMP000', 'IN16/0004', 3.2),
								 ('CS000', 'IN16/0005', 2.9),
								 ('JOURN000', 'IN16/0006', 3.2),
								 ('SOEN000', 'IN16/0003', 3.8);

							
-- --------------------------------------------------------
--
-- Table structure for table `schooldept`
--

CREATE TABLE `registeredunits` (
  `Std_ID` varchar(64) NOT NULL PRIMARY KEY ,
  `unitCode` varchar(64) NOT NULL,
  `isApproved` varchar(4) NULL DEFAULT 'No',
  FOREIGN KEY(Std_ID) REFERENCES students(Std_ID),
  FOREIGN KEY(unitCode) REFERENCES courseunits(unitCode);
  
);

--
-- Dumping data for table `registeredunits`
--

INSERT INTO `registeredunits` VALUES 
('IN16/0005', 'SOEN000','No'),
('IN16/0005', 'ACMP000','No'),
('IN16/0004', 'SOEN000','No'),
('IN16/0006', 'SOEN000','No'),
('IN16/00010', 'JOURN000','No'),
('IN16/00011', 'CS000','No');

-- SELECT * from registeredunits;

-- --------------------------------------------------------

CREATE TABLE `billstatement` (
  `Std_ID` varchar(255) NOT NULL PRIMARY KEY,
  `feeAmount` decimal(10,2) NOT NULL
--  FOREIGN KEY (Std_ID) REFERENCES students(Std_ID)

);
INSERT INTO `billstatement` VALUES
('IN16/0003', 25000);

-- Table Structure For Graduation Eligibility
CREATE TABLE `graduationEligible` (
  `Std_ID` varchar(255) NOT NULL PRIMARY KEY,
  `isEligible` varchar(4) NULL DEFAULT 'No'
--  FOREIGN KEY (Std_ID) REFERENCES students(Std_ID)

);
INSERT INTO `graduationEligible` VALUES
('IN16/00010', 'No'),
('IN16/00011','No'),
('IN16/00012', 'No'),
('IN16/00013', 'No'),
('IN16/0003', 'No'),
('IN16/0004', 'No'),
('IN16/0005', 'No'),
('IN16/0006', 'No'),
('IN16/0007', 'No'),
('IN16/0008', 'No'),
('IN16/0009', 'No'),
('IN17/0001', 'No');
--
-- Table structure for table `Transcripts`
--

-- CREATE TABLE `Transcripts` (
--   `Std_ID` varchar(255) NOT NULL
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `Transcripts`
--

-- INSERT INTO `Transcripts` (`Std_ID`) VALUES
-- ('IN16/0003'),
-- ('IN16/0004'),
-- ('IN16/0005'),
-- ('IN16/0006');


--
-- Indexes for dumped tables
--

--
-- Indexes for table `Courses`
--
-- ALTER TABLE `Courses`
--   ADD PRIMARY KEY (`Course_ID`),
--   ADD KEY `Std_ID` (`Std_ID`);

--
-- Indexes for table `Course_Advisor`
--
-- ALTER TABLE `Course_Advisor`
--   ADD PRIMARY KEY (`Staff_No`);

--
-- Indexes for table `Course_Units`
--
-- ALTER TABLE `Course_Units`
--   ADD PRIMARY KEY (`Unit_Code`),
--   ADD KEY `Course_ID` (`Course_ID`);

--
-- Indexes for table `Student`
--
-- ALTER TABLE `Student`
--   ADD PRIMARY KEY (`Std_ID`);

--
-- Indexes for table `Transcripts`
--
-- ALTER TABLE `Transcripts`
--   ADD PRIMARY KEY (`Std_ID`);

--
-- Indexes for table `Unit_Results`
--
-- ALTER TABLE `Unit_Results`
--   ADD PRIMARY KEY (`Unit_Code`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Courses`
--
-- ALTER TABLE `Courses`
--   MODIFY `Course_ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `Course_Advisor`
--
-- ALTER TABLE `Course_Advisor`
--   MODIFY `Staff_No` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Courses`
--
-- ALTER TABLE `Courses`
  -- ADD CONSTRAINT `Courses_ibfk_1` FOREIGN KEY (`Std_ID`) REFERENCES `Student` (`Std_ID`);

--
-- Constraints for table `Course_Units`
--
-- ALTER TABLE `courseunits`
--   ADD CONSTRAINT `coursenits_ibfk_1` FOREIGN KEY (`Course_ID`) REFERENCES `Courses` (`Course_ID`);

-- --
-- Constraints for table `Transcripts`
--
-- ALTER TABLE `Transcripts`
--   ADD CONSTRAINT `Transcripts_ibfk_1` FOREIGN KEY (`Std_ID`) REFERENCES `Student` (`Std_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
