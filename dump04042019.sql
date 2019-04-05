-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: masstile.com    Database: masstile_zource
-- ------------------------------------------------------
-- Server version	5.7.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Accounts`
--

DROP TABLE IF EXISTS `Accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Accounts` (
  `USER_NAME` varchar(20) NOT NULL,
  `ACTIVE` bit(1) NOT NULL,
  `ENCRYTED_PASSWORD` varchar(128) NOT NULL,
  `USER_ROLE` varchar(20) NOT NULL,
  PRIMARY KEY (`USER_NAME`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Accounts`
--

LOCK TABLES `Accounts` WRITE;
/*!40000 ALTER TABLE `Accounts` DISABLE KEYS */;
INSERT INTO `Accounts` VALUES ('user','','$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu','ROLE_USER'),('admin','','$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu','ROLE_ADMIN');
/*!40000 ALTER TABLE `Accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BrandCategories`
--

DROP TABLE IF EXISTS `BrandCategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BrandCategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  `image` varchar(100) DEFAULT NULL,
  `brandId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `brandID_FK_idx` (`brandId`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BrandCategories`
--

LOCK TABLES `BrandCategories` WRITE;
/*!40000 ALTER TABLE `BrandCategories` DISABLE KEYS */;
INSERT INTO `BrandCategories` VALUES (1,'Category1',NULL,NULL,1),(2,'Category 2',NULL,NULL,1);
/*!40000 ALTER TABLE `BrandCategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Brands`
--

DROP TABLE IF EXISTS `Brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Brands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `logo` varchar(255) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Brands`
--

LOCK TABLES `Brands` WRITE;
/*!40000 ALTER TABLE `Brands` DISABLE KEYS */;
INSERT INTO `Brands` VALUES (1,'MASS TILE','mass_tile_logo.png','tile','zsadfas fadsed'),(2,'Glazzio','glazzio-tiles.jpg','tile','Glazzio Desc'),(3,'Laticrete','laticrete_logo.svg','installation',NULL),(4,'Schluter Systems','schluter_systems.jpg','installation',NULL),(5,'Anatolia Tile','anatolia-tile.jpg','tile',NULL),(6,'Casalgrande Padana','casalgrande_padana_logo.jpg','tile',NULL),(7,'1',NULL,'tile',NULL);
/*!40000 ALTER TABLE `Brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Categories`
--

DROP TABLE IF EXISTS `Categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `image` varchar(45) DEFAULT NULL,
  `top_banner` varchar(45) DEFAULT NULL,
  `brand_id` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Categories`
--

LOCK TABLES `Categories` WRITE;
/*!40000 ALTER TABLE `Categories` DISABLE KEYS */;
INSERT INTO `Categories` VALUES (1,'ROOT CATEGORY',NULL,NULL,NULL,NULL),(2,'Tile','','tile.jpg','tile-banner.jpg','3'),(3,'Cabinetry','','kitchen-cabinets.jpg','kitchen-design-banner-top.jpeg',NULL),(4,'Countertops','sdfsd ','12.jpg',NULL,NULL),(5,'Installation Materials',NULL,NULL,NULL,'2'),(6,'Cat 6',NULL,NULL,NULL,'2');
/*!40000 ALTER TABLE `Categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Categories_Parents`
--

DROP TABLE IF EXISTS `Categories_Parents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Categories_Parents` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL,
  PRIMARY KEY (`category_id`,`parent_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Categories_Parents`
--

LOCK TABLES `Categories_Parents` WRITE;
/*!40000 ALTER TABLE `Categories_Parents` DISABLE KEYS */;
INSERT INTO `Categories_Parents` VALUES (2,1),(2,3),(3,1),(4,1),(4,3),(5,3);
/*!40000 ALTER TABLE `Categories_Parents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Order_Details`
--

DROP TABLE IF EXISTS `Order_Details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Order_Details` (
  `ID` varchar(50) NOT NULL,
  `Amount` double NOT NULL,
  `Price` double NOT NULL,
  `Quanity` int(11) NOT NULL,
  `ORDER_ID` varchar(50) NOT NULL,
  `PRODUCT_ID` varchar(20) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `ORDER_DETAIL_ORD_FK` (`ORDER_ID`),
  KEY `ORDER_DETAIL_PROD_FK` (`PRODUCT_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Order_Details`
--

LOCK TABLES `Order_Details` WRITE;
/*!40000 ALTER TABLE `Order_Details` DISABLE KEYS */;
/*!40000 ALTER TABLE `Order_Details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Orders` (
  `ID` varchar(50) NOT NULL,
  `Amount` double NOT NULL,
  `Customer_Address` varchar(255) NOT NULL,
  `Customer_Email` varchar(128) NOT NULL,
  `Customer_Name` varchar(255) NOT NULL,
  `Customer_Phone` varchar(128) NOT NULL,
  `Order_Date` datetime NOT NULL,
  `Order_Num` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UK_sxhpvsj665kmi4f7jdu9d2791` (`Order_Num`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Pages`
--

DROP TABLE IF EXISTS `Pages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Pages` (
  `page_id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `metaTitle` varchar(255) DEFAULT NULL,
  `metaKeywords` varchar(255) DEFAULT NULL,
  `metaDescription` varchar(255) DEFAULT NULL,
  `html_content` mediumtext,
  `enabled` tinyint(1) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `side_html` mediumtext,
  PRIMARY KEY (`page_id`),
  KEY `PAGE_MENU_FK_idx` (`menu_id`),
  CONSTRAINT `PAGE_MENU_FK` FOREIGN KEY (`menu_id`) REFERENCES `page_menus` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Pages`
--

LOCK TABLES `Pages` WRITE;
/*!40000 ALTER TABLE `Pages` DISABLE KEYS */;
INSERT INTO `Pages` VALUES (1,'Contacts','Contacts - Our Stores','contact us, store location, working hours, business information','Contact information','           <form th:object=\"${emailForm}\" th:action=\"@{/contactFormEmail}\" method=\"post\">\n		    <fieldset>\n			\n			<div th:if=\"${errorMessage!= null}\" class=\"error-message\" th:utext=\"${errorMessage}\">\n			\n				<div class=\"form-group\">\n                    <label for=\"InputName\">Your Name (*)</label>\n                    <input type=\"text\" th:field=\"*{name}\" class=\"form-control\" placeholder=\"Enter name\">\n					<span class=\"text-danger\" th:if=\"${#fields.hasErrors(\'name\')}\" th:errors=\"*{name}\"></span>\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"InputEmail1\">Email address (*)</label>\n                    <input type=\"email\" th:field=\"*{email}\" class=\"form-control\" required=\"required\" placeholder=\"Enter email\">\n					<span class=\"text-danger\" th:if=\"${#fields.hasErrors(\'email\')}\" th:errors=\"*{email}\"></span>\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"InputSubject\">Subject</label>\n                    <input type=\"text\" th:field=\"*{subject}\" class=\"form-control\" placeholder=\"Enter subject\">\n					<span class=\"text-danger\" th:if=\"${#fields.hasErrors(\'subject\')}\" th:errors=\"*{subject}\"></span>\n                </div>\n                <div class=\"form-group\">\n                    <label for=\"InputMessage\">Message (*)</label>\n                    <textarea th:field=\"*{message}\" rows=\"12\" class=\"form-control\" ></textarea>\n					<span class=\"text-danger\" th:if=\"${#fields.hasErrors(\'message\')}\" th:errors=\"*{message}\"></span>\n                </div>\n				</fieldset>\n				\n                <button type=\"submit\" class=\"btn btn-theme btn-sm\">Submit</button>\n				\n     		</form>',1,1,NULL),(2,'About Us','About Us','tile, stone, countertops, about us, needham, ma','Our business','\r\n            <img src=\"/images/banners/kitchen_design_service.jpg\" alt=\"Our Services\" class=\"img-thumbnail img-fluid w-100 mb-3\">\r\n            <blockquote class=\"blockquote my-3 text-center\">\r\n                <p>You dream, we design – this is our ultimate vision. MASS TILE specializes in offering tiles, kitchen\r\n                    cabinets, lightning and bathroom fixtures. If you are planning home renovation, we are here for you\r\n                    to enlighten you on details of our design service.</p>\r\n            </blockquote>\r\n\r\n            <!--Accordion-->\r\n            <div id=\"accordion\" class=\"my-3\" role=\"tablist\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"headingOne\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapseOne\" aria-expanded=\"false\" aria-controls=\"collapseOne\">\r\n                            Kitchen Cabinets\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapseOne\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"headingOne\" data-parent=\"#accordion\">\r\n                        <div class=\"card-body\">\r\n                            <p>We offer top quality products, experienced kitchen designing and installing service at an\r\n                                affordable price.</p>\r\n                            <p>We are also present on social media. Go ahead and follow us on Houzz and connect with us\r\n                                on Facebook for design inspiration, hints and tips.</p>\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"headingTwo\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapseTwo\" aria-expanded=\"false\" aria-controls=\"collapseTwo\">\r\n                            Tile Selection\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapseTwo\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"headingTwo\" data-parent=\"#accordion\">\r\n                        <div class=\"card-body\">\r\n\r\n                            <p> We do our best to ensure the installation of your new tiles or kitchen is undertaken\r\n                                with fewer worries in the quickest and most efficient way.</p>\r\n                            <p>Our excellent project management, quality installation and overall customer service that\r\n                                is specifically tailored to every client are often mentioned as why our customers choose\r\n                                and recommend us.</p>\r\n\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"headingFive\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapseFive\" aria-expanded=\"false\" aria-controls=\"collapseFive\">\r\n                            Installation and Project Management\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapseFive\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"headingFive\" data-parent=\"#accordion\">\r\n                        <div class=\"card-body\">\r\n\r\n                            <p> We do our best to ensure the installation of your new tiles or kitchen is undertaken\r\n                                with fewer worries in the quickest and most efficient way.</p>\r\n                            <p>Our excellent project management, quality installation and overall customer service that\r\n                                is specifically tailored to every client are often mentioned as why our customers choose\r\n                                and recommend us.</p>\r\n\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n            <!--//Accordion-->\r\n\r\n\r\n            <!--Matterport-->\r\n            <div class=\"row my-2\">\r\n                <div class=\"col-md-6 justify-content-md-center text-center\">\r\n                    <h3>Needham Showroom</h3>\r\n                    <iframe class=\".embed-responsive-item\" src=\"https://my.matterport.com/show/?m=kfDdz6QLkdP\" width=\"400\" height=\"270\" allowfullscreen=\"\"></iframe>\r\n                </div>\r\n\r\n                <div class=\"col-md-6 justify-content-md-center text-center\">\r\n                    <h3>Sharon Showroom/Warehouse</h3>\r\n                    <iframe class=\".embed-responsive-item\" src=\"https://my.matterport.com/show/?m=WGDzZjfQ96e\" width=\"400\" height=\"270\" allowfullscreen=\"\"></iframe>\r\n                </div>\r\n            </div>\r\n            <!--Matterport-->\r\n\r\n\r\n        \r\n\r\n',1,1,'\r\n            <div class=\"title\">Testimonials</div>\r\n            <iframe scrolling=\"no\" frameborder=\"0\" width=\"300\" height=\"435\" style=\"border: none;\" src=\"https://www.houzz.com/reviewWidget/bostonkitchens/\"></iframe>\r\n            <script>(function (d, s, id) {\r\n                if (!d.getElementById(id)) {\r\n                    var js = d.createElement(s);\r\n                    js.id = id;\r\n                    js.async = true;\r\n                    js.src = \"//platform.houzz.com/js/widgets.js?\" + (new Date().getTime());\r\n                    var ss = d.getElementsByTagName(s)[0];\r\n                    ss.parentNode.insertBefore(js, ss);\r\n                }\r\n            })(document, \"script\", \"houzzwidget-js\");</script>\r\n'),(3,'Frequently Asked Questions (FAQ)','Frequently Asked Questions (FAQ)','questions, answers, faq, payment, delivery, returns','Frequently Asked Questions','<div class=\"row\">\r\n     \r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">shopping_cart</i> Shopping</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion1\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading1-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse1-1\" aria-expanded=\"false\" aria-controls=\"collapse1-1\">\r\n                            I see different prices with the same title. Why?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse1-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading1-1\" data-parent=\"#accordion1\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading1-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse1-2\" aria-expanded=\"false\" aria-controls=\"collapse1-2\">\r\n                            Why do I see different prices for the same product?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse1-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading1-2\" data-parent=\"#accordion1\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Provident blanditiis ipsa expedita, earum esse omnis delectus possimus fugit perferendis ex veritatis veniam consequuntur mollitia, facilis vel velit voluptatem eos ut!\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading1-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse1-3\" aria-expanded=\"false\" aria-controls=\"collapse1-3\">\r\n                            Is it necessary to have an account to shop on Marketshop?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse1-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading1-3\" data-parent=\"#accordion1\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nemo, quisquam, corrupti. Perspiciatis maxime provident in vero dolore similique quam voluptatum eum reiciendis ex repellat a saepe, explicabo odit quae perferendis!\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading1-4\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse1-4\" aria-expanded=\"false\" aria-controls=\"collapse1-4\">\r\n                            What do I need to know before getting an order gift wrapped?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse1-4\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading1-4\" data-parent=\"#accordion1\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab perferendis, similique a accusamus ipsum incidunt repellendus quis, soluta, minus molestiae illum eligendi id hic eum accusantium voluptatem quae facilis architecto.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading1-5\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse1-5\" aria-expanded=\"false\" aria-controls=\"collapse1-5\">\r\n                            What is Advantage?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse1-5\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading1-5\" data-parent=\"#accordion1\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">monetization_on</i> Payments</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion2\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-1\" aria-expanded=\"false\" aria-controls=\"collapse2-1\">\r\n                            How do I pay for a purchase?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-1\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-2\" aria-expanded=\"false\" aria-controls=\"collapse2-2\">\r\n                            Are there any hidden charges (Octroi or Sales Tax) when I make a purchase?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-2\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Provident blanditiis ipsa expedita, earum esse omnis delectus possimus fugit perferendis ex veritatis veniam consequuntur mollitia, facilis vel velit voluptatem eos ut!\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-3\" aria-expanded=\"false\" aria-controls=\"collapse2-3\">\r\n                            What is Cash on Delivery?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-3\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Nemo, quisquam, corrupti. Perspiciatis maxime provident in vero dolore similique quam voluptatum eum reiciendis ex repellat a saepe, explicabo odit quae perferendis!\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-4\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-4\" aria-expanded=\"false\" aria-controls=\"collapse2-4\">\r\n                            How do I pay using a credit/debit card?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-4\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-4\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ab perferendis, similique a accusamus ipsum incidunt repellendus quis, soluta, minus molestiae illum eligendi id hic eum accusantium voluptatem quae facilis architecto.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-5\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-5\" aria-expanded=\"false\" aria-controls=\"collapse2-5\">\r\n                            Is it safe to use my credit/debit card on this store?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-5\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-5\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-6\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-6\" aria-expanded=\"false\" aria-controls=\"collapse2-6\">\r\n                            What is a 3D Secure password?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-6\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-6\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-7\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-7\" aria-expanded=\"false\" aria-controls=\"collapse2-7\">\r\n                            How can I get the 3D Secure password for my credit/debit card?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-7\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-7\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-8\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-8\" aria-expanded=\"false\" aria-controls=\"collapse2-8\">\r\n                            Can I use my bank\'s Internet Banking feature to make a payment?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-8\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-8\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-9\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-9\" aria-expanded=\"false\" aria-controls=\"collapse2-9\">\r\n                            Can I make a credit/debit card or Internet Banking payment through my mobile?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-9\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-9\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-10\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-10\" aria-expanded=\"false\" aria-controls=\"collapse2-10\">\r\n                            How does \'Instant Cashback\' work?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-10\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-10\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading2-11\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse2-11\" aria-expanded=\"false\" aria-controls=\"collapse2-11\">\r\n                            How do I place a Cash on Delivery (COD) order?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse2-11\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading2-11\" data-parent=\"#accordion2\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Corporis, culpa rem in voluptate inventore repudiandae officia ad eveniet aut reiciendis corrupti, odit, velit officiis voluptatibus, incidunt minima omnis voluptas. Similique.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">person</i> My Account &amp; My Orders</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion3\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading3-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse3-1\" aria-expanded=\"false\" aria-controls=\"collapse3-1\">\r\n                            What is \'My Account\'? How do I update my information ?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse3-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading3-1\" data-parent=\"#accordion3\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading3-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse3-2\" aria-expanded=\"false\" aria-controls=\"collapse3-2\">\r\n                            How do I merge my accounts linked to different email ids?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse3-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading3-2\" data-parent=\"#accordion3\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading3-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse3-3\" aria-expanded=\"false\" aria-controls=\"collapse3-3\">\r\n                            How do I know my order has been confirmed?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse3-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading3-3\" data-parent=\"#accordion3\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading3-4\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse3-4\" aria-expanded=\"false\" aria-controls=\"collapse3-4\">\r\n                            Can I order a product that is \'Out of Stock\'?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse3-4\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading3-4\" data-parent=\"#accordion3\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">card_giftcard</i> Gift Voucher</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion4\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-1\" aria-expanded=\"false\" aria-controls=\"collapse4-1\">\r\n                            How do I buy/gift an e-Gift Voucher?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-1\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-2\" aria-expanded=\"false\" aria-controls=\"collapse4-2\">\r\n                            How do I make a purchase with an e-Gift Voucher?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-2\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-3\" aria-expanded=\"false\" aria-controls=\"collapse4-3\">\r\n                            Does an e-Gift Voucher expire?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-3\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-4\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-4\" aria-expanded=\"false\" aria-controls=\"collapse4-4\">\r\n                            Can I use promotional codes with e-Gift Vouchers?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-4\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-4\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-5\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-5\" aria-expanded=\"false\" aria-controls=\"collapse4-5\">\r\n                            Is there a limit on how many e-Gift vouchers I can use on a single order?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-5\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-5\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading4-6\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse4-6\" aria-expanded=\"false\" aria-controls=\"collapse4-6\">\r\n                            What Terms &amp; Conditions apply to e-Gift Vouchers?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse4-6\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading4-6\" data-parent=\"#accordion4\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">help</i> Order Status</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion5\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading5-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse5-1\" aria-expanded=\"false\" aria-controls=\"collapse5-1\">\r\n                            How do I check the current status of my orders?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse5-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading5-1\" data-parent=\"#accordion5\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading5-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse5-2\" aria-expanded=\"false\" aria-controls=\"collapse5-2\">\r\n                            What do the different order status mean?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse5-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading5-2\" data-parent=\"#accordion5\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading5-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse5-3\" aria-expanded=\"false\" aria-controls=\"collapse5-3\">\r\n                            When and how can I cancel an order?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse5-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading5-3\" data-parent=\"#accordion5\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-md-3\">\r\n            <h5><i class=\"material-icons\">local_shipping</i> Shipping</h5>\r\n        </div>\r\n        <div class=\"col-md-9 mb-4\">\r\n            <div id=\"accordion6\" role=\"tablist\" class=\"mb-3\">\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-1\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-1\" aria-expanded=\"false\" aria-controls=\"collapse6-1\">\r\n                            What are the delivery charges?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-1\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-1\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-2\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-2\" aria-expanded=\"false\" aria-controls=\"collapse6-2\">\r\n                            Are there any hidden costs (sales tax, octroi etc) on items sold by sellers?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-2\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-2\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-3\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-3\" aria-expanded=\"false\" aria-controls=\"collapse6-3\">\r\n                            What is the estimated delivery time?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-3\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-3\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-4\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-4\" aria-expanded=\"false\" aria-controls=\"collapse6-4\">\r\n                            Why does the estimated delivery time vary from seller to seller?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-4\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-4\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-5\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-5\" aria-expanded=\"false\" aria-controls=\"collapse6-5\">\r\n                            Why does the delivery date not correspond to the delivery timeline mentioned?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-5\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-5\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-6\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-6\" aria-expanded=\"false\" aria-controls=\"collapse6-6\">\r\n                            Seller does not/cannot ship to my area. Why?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-6\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-6\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-7\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-7\" aria-expanded=\"false\" aria-controls=\"collapse6-7\">\r\n                            I need to return an item, how do I arrange for a pick-up?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-7\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-7\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n                <div class=\"card mb-1\">\r\n                    <div class=\"card-header p-2\" role=\"tab\" id=\"heading6-8\">\r\n                        <a class=\"collapsed text-secondary font-weight-bold\" data-toggle=\"collapse\" href=\"#collapse6-8\" aria-expanded=\"false\" aria-controls=\"collapse6-8\">\r\n                            Does deliver internationally?\r\n                        </a>\r\n                    </div>\r\n                    <div id=\"collapse6-8\" class=\"collapse\" role=\"tabpanel\" aria-labelledby=\"heading6-8\" data-parent=\"#accordion6\">\r\n                        <div class=\"card-body\">\r\n                            Lorem ipsum dolor sit amet, consectetur adipisicing elit. Veniam unde dicta ab sapiente harum beatae nihil aspernatur nam eos perferendis. Iusto perspiciatis assumenda vitae aspernatur nam est fuga maiores sequi.\r\n                        </div>\r\n                    </div>\r\n                </div>\r\n            </div>\r\n        </div>\r\n        <div class=\"col-12\">\r\n            <div class=\"alert alert-info\" role=\"alert\">\r\n                Was this article helpful ? <a href=\"#\" class=\"alert-link\">Yes</a> / <a href=\"contact.html\" class=\"alert-link\">No, I want to contact support</a>\r\n            </div>\r\n        </div>\r\n    </div>',1,1,NULL);
/*!40000 ALTER TABLE `Pages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Products`
--

DROP TABLE IF EXISTS `Products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Products` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `sku` varchar(55) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `image` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL,
  `create_date` datetime NOT NULL,
  `enabled` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `sku_UNIQUE` (`sku`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Products`
--

LOCK TABLES `Products` WRITE;
/*!40000 ALTER TABLE `Products` DISABLE KEYS */;
INSERT INTO `Products` VALUES (1,'Core Java','sku001','Descrer ',100,NULL,2,'2018-07-17 20:11:50',1),(3,'Swift for Beginners','sku002',NULL,120,NULL,2,'2018-07-17 20:11:50',1),(4,'Oracle XML Parser','sku003',NULL,120,NULL,3,'2018-07-17 20:11:50',0),(5,'CSharp Tutorial for Beginers','sku004',NULL,110,NULL,2,'2018-07-17 20:11:50',1);
/*!40000 ALTER TABLE `Products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `URL_Redirect`
--

DROP TABLE IF EXISTS `URL_Redirect`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `URL_Redirect` (
  `url_redirect_id` int(11) NOT NULL AUTO_INCREMENT,
  `seo_url` varchar(255) DEFAULT NULL,
  `original_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`url_redirect_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `URL_Redirect`
--

LOCK TABLES `URL_Redirect` WRITE;
/*!40000 ALTER TABLE `URL_Redirect` DISABLE KEYS */;
INSERT INTO `URL_Redirect` VALUES (1,'/about_us','/page/2'),(2,'/glazzio_tiles2','/brands'),(3,'/tile','/category/2'),(4,'/contacts1','/page/1'),(5,'/faq','/page/3');
/*!40000 ALTER TABLE `URL_Redirect` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `page_menus`
--

DROP TABLE IF EXISTS `page_menus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `page_menus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(125) DEFAULT NULL,
  `title` varchar(120) DEFAULT NULL,
  `content` mediumtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `page_menus`
--

LOCK TABLES `page_menus` WRITE;
/*!40000 ALTER TABLE `page_menus` DISABLE KEYS */;
INSERT INTO `page_menus` VALUES (1,'Main Menu','Categories','<ul class=\"list-group mb-4\">\n                    <li class=\"list-group-item pl-2 border-left-0 border-right-0 border-top-0\">» 212 Lorem Ipsum. Dolor Sit, Amet</li>\n                    <li class=\"list-group-item pl-2 border-left-0 border-right-0\">» +123-456-789</li>\n                    <li class=\"list-group-item pl-2 border-left-0 border-right-0\">» cs@domain.tld</li>\n                </ul>\n                              \n                <div class=\"title\"><span>Our Location</span></div>\n\n\n\n<div class=\"my-4 justify-content-md-center\">\n\n<div id=\"googleMap\" class=\"container-fluid\" style=\"height: 450px;\">\n\n\n\n</div>\n\n<script>\n    // Initialize and add the map\n    function initMap() {\n        // The location\n        var pos1 = {lat: 42.3054946, lng: -71.21772479999998};\n        var pos2 = {lat: 42.1180132, lng: -71.23459500000001};\n        var centerPos = {lat:  42.206307, lng: -71.193444};\n        // The map, centered at pos1\n        var map = new google.maps.Map(\n\n            document.getElementById(\'googleMap\'), {zoom: 10, center: centerPos});\n        // The marker, positioned\n        var marker1 = new google.maps.Marker({\n            position: pos1,\n            map: map,\n            title:\"Mass Tile - Home Design Center\"\n\n        });\n\n        var marker2 = new google.maps.Marker({\n            position: pos2,\n            map: map,\n            title:\"Mass Tile\"\n        });\n\n\n        // Info Windows\n        var infowindow1 = new google.maps.InfoWindow({\n            content: \"<b>Mass Tile - Home Design Center</b>\" +\n            \"</br>Tel: (781) 400-2614\" +\n                \"\"\n\n        });\n        var infowindow2 = new google.maps.InfoWindow({\n            content: \"<b>MASS TILE</b> </br>\" +\n                \"Tel: (781) 806-0220\"\n        });\n        // Attach it to the marker we\'ve just added\n        google.maps.event.addListener(marker1, \'click\', function() {\n            infowindow1.open(map,marker1);\n        });\n        google.maps.event.addListener(marker2, \'click\', function() {\n            infowindow2.open(map,marker2);\n        });\n\n    }\n</script>\n<!--Load the API from the specified URL\n* The async attribute allows the browser to render the page while the API loads\n* The key parameter will contain your own API key (which is not needed for this tutorial)\n* The callback parameter executes the initMap() function\n-->\n<script async defer\n        src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyCfeTm9R9V13I3SMqqbhamwxOf6dYZJyxg&callback=initMap\">\n</script>\n\n</div>'),(2,'Candlelight Menu','Categories',NULL);
/*!40000 ALTER TABLE `page_menus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit_of_measure`
--

DROP TABLE IF EXISTS `unit_of_measure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit_of_measure` (
  `idunit_of_measure` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idunit_of_measure`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit_of_measure`
--

LOCK TABLES `unit_of_measure` WRITE;
/*!40000 ALTER TABLE `unit_of_measure` DISABLE KEYS */;
INSERT INTO `unit_of_measure` VALUES (1,'pc'),(2,'sqft'),(3,'box'),(4,'sheet');
/*!40000 ALTER TABLE `unit_of_measure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'masstile_zource'
--

--
-- Dumping routines for database 'masstile_zource'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-04-04 18:54:32
