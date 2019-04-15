--
-- PostgreSQL database dump
--

-- Dumped from database version 11.2 (Debian 11.2-1.pgdg90+1)
-- Dumped by pg_dump version 11.2 (Ubuntu 11.2-1.pgdg18.04+1)

-- Started on 2019-04-15 23:54:12 +03

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE manerp;
--
-- TOC entry 3321 (class 1262 OID 16384)
-- Name: manerp; Type: DATABASE; Schema: -; Owner: manerp
--

CREATE DATABASE manerp WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8';


ALTER DATABASE manerp OWNER TO manerp;

\connect manerp

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 9 (class 2615 OID 28941)
-- Name: manerp; Type: SCHEMA; Schema: -; Owner: manerp
--

CREATE SCHEMA manerp;


ALTER SCHEMA manerp OWNER TO manerp;

--
-- TOC entry 1 (class 3079 OID 26721)
-- Name: adminpack; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS adminpack WITH SCHEMA pg_catalog;


--
-- TOC entry 3322 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION adminpack; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION adminpack IS 'administrative functions for PostgreSQL';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 198 (class 1259 OID 28943)
-- Name: arvento_device; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.arvento_device (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    active boolean,
    device_id character varying(100) NOT NULL,
    dorset_id character varying(32),
    sys_company_id character varying(32) NOT NULL,
    vehicle_id character varying(32),
    name character varying(50)
);


ALTER TABLE manerp.arvento_device OWNER TO manerp;

--
-- TOC entry 234 (class 1259 OID 29702)
-- Name: company; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.company (
    id character varying(32) NOT NULL,
    web_address character varying(50),
    date_created timestamp without time zone,
    employer_registration_no character varying(30),
    active boolean,
    phone2 character varying(20),
    sysref_company_type_id character varying(32) NOT NULL,
    sysref_nace_code_id character varying(32),
    sysref_city_id character varying(32),
    sysref_country_id character varying(32),
    customer_representative character varying(255),
    name character varying(50) NOT NULL,
    ref_company_sector_id character varying(32),
    tax_number character varying(50) NOT NULL,
    tax_office character varying(255) NOT NULL,
    phone character varying(20) NOT NULL,
    fax character varying(20),
    code character varying(255),
    sysref_district_id character varying(32),
    last_updated timestamp without time zone,
    gsm character varying(20),
    title character varying(50) NOT NULL,
    address character varying(255) NOT NULL,
    trade_registration_no character varying(30),
    sys_company_id character varying(32) NOT NULL,
    number_of_staff integer,
    email character varying(50)
);


ALTER TABLE manerp.company OWNER TO manerp;

--
-- TOC entry 199 (class 1259 OID 28956)
-- Name: expenditure; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.expenditure (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    active boolean
);


ALTER TABLE manerp.expenditure OWNER TO manerp;

--
-- TOC entry 200 (class 1259 OID 28961)
-- Name: location; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.location (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    active boolean,
    latitude character varying(100),
    longitude character varying(100),
    sys_company_id character varying(32) NOT NULL,
    name character varying(100)
);


ALTER TABLE manerp.location OWNER TO manerp;

--
-- TOC entry 201 (class 1259 OID 28966)
-- Name: order; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp."order" (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    sysref_order_state_id character varying(32) NOT NULL,
    active boolean,
    voyage_id character varying(32),
    order_date timestamp without time zone NOT NULL,
    sysref_revenue_type_id character varying(32),
    company_id character varying(32) NOT NULL,
    sys_company_id character varying(32) NOT NULL,
    work_order_no character varying(30),
    name character varying(50) NOT NULL,
    billing_no character varying(50) NOT NULL
);


ALTER TABLE manerp."order" OWNER TO manerp;

--
-- TOC entry 202 (class 1259 OID 28971)
-- Name: ref_company_sector; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.ref_company_sector (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32),
    name character varying(50) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.ref_company_sector OWNER TO manerp;

--
-- TOC entry 203 (class 1259 OID 28976)
-- Name: ref_staff_title; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.ref_staff_title (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.ref_staff_title OWNER TO manerp;

--
-- TOC entry 204 (class 1259 OID 28981)
-- Name: ref_working_area; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.ref_working_area (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.ref_working_area OWNER TO manerp;

--
-- TOC entry 205 (class 1259 OID 28986)
-- Name: revenue; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.revenue (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    active boolean
);


ALTER TABLE manerp.revenue OWNER TO manerp;

--
-- TOC entry 206 (class 1259 OID 28991)
-- Name: semi_trailer; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.semi_trailer (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    active boolean,
    plate_number character varying(255) NOT NULL,
    brand character varying(50),
    sys_company_id character varying(32) NOT NULL,
    name character varying(50)
);


ALTER TABLE manerp.semi_trailer OWNER TO manerp;

--
-- TOC entry 207 (class 1259 OID 28996)
-- Name: semi_truck; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.semi_truck (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    active boolean,
    plate_number character varying(255) NOT NULL,
    brand character varying(50) NOT NULL,
    sys_company_id character varying(32) NOT NULL,
    name character varying(50)
);


ALTER TABLE manerp.semi_truck OWNER TO manerp;

--
-- TOC entry 208 (class 1259 OID 29001)
-- Name: staff; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.staff (
    id character varying(32) NOT NULL,
    driving_license_number character varying(30),
    date_created timestamp without time zone,
    first_name character varying(30) NOT NULL,
    gsm_no character varying(15) NOT NULL,
    active boolean,
    photo_mime_type character varying(255),
    sysref_city_id character varying(32),
    sysref_country_id character varying(32),
    user_id character varying(32),
    ref_staff_title_id character varying(32) NOT NULL,
    photo_name character varying(255),
    last_name character varying(50) NOT NULL,
    tc_id_number bigint NOT NULL,
    code character varying(8) NOT NULL,
    sysref_district_id character varying(32),
    sysref_driving_type_id character varying(32),
    last_updated timestamp without time zone,
    photo bytea,
    middle_name character varying(30),
    home_phone character varying(15),
    address character varying(255),
    sys_company_id character varying(32) NOT NULL,
    sysref_staff_contract_type_id character varying(32) NOT NULL,
    birth_date timestamp without time zone,
    sysref_gender_id character varying(32),
    email character varying(50) NOT NULL
);


ALTER TABLE manerp.staff OWNER TO manerp;

--
-- TOC entry 209 (class 1259 OID 29009)
-- Name: staff_document; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.staff_document (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    active boolean,
    document_name character varying(255) NOT NULL,
    staff_id character varying(32) NOT NULL,
    document bytea NOT NULL,
    document_mime_type character varying(255) NOT NULL
);


ALTER TABLE manerp.staff_document OWNER TO manerp;

--
-- TOC entry 210 (class 1259 OID 29017)
-- Name: sys_company; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sys_company (
    id character varying(32) NOT NULL,
    web_address character varying(50),
    date_created timestamp without time zone,
    employer_registration_no character varying(30),
    active boolean,
    phone2 character varying(20),
    sysref_nace_code_id character varying(32),
    sysref_city_id character varying(32),
    sysref_country_id character varying(32),
    customer_representative character varying(255),
    name character varying(50) NOT NULL,
    ref_company_sector_id character varying(32),
    tax_number character varying(50),
    tax_office character varying(255),
    phone character varying(20) NOT NULL,
    fax character varying(20),
    sysref_district_id character varying(32),
    last_updated timestamp without time zone,
    gsm character varying(20),
    title character varying(50),
    address character varying(255) NOT NULL,
    trade_registration_no character varying(30),
    number_of_staff integer,
    email character varying(50)
);


ALTER TABLE manerp.sys_company OWNER TO manerp;

--
-- TOC entry 211 (class 1259 OID 29025)
-- Name: sysref_city; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_city (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(2),
    last_updated timestamp without time zone,
    active boolean,
    sysref_country_id character varying(32) NOT NULL,
    name character varying(19) NOT NULL
);


ALTER TABLE manerp.sysref_city OWNER TO manerp;

--
-- TOC entry 212 (class 1259 OID 29030)
-- Name: sysref_company_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_company_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(100)
);


ALTER TABLE manerp.sysref_company_type OWNER TO manerp;

--
-- TOC entry 213 (class 1259 OID 29035)
-- Name: sysref_country; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_country (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(2),
    last_updated timestamp without time zone,
    active boolean,
    name character varying(33) NOT NULL
);


ALTER TABLE manerp.sysref_country OWNER TO manerp;

--
-- TOC entry 214 (class 1259 OID 29040)
-- Name: sysref_delivery_status; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_delivery_status (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(100)
);


ALTER TABLE manerp.sysref_delivery_status OWNER TO manerp;

--
-- TOC entry 215 (class 1259 OID 29045)
-- Name: sysref_district; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_district (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(4),
    last_updated timestamp without time zone,
    active boolean,
    sysref_city_id character varying(32) NOT NULL,
    name character varying(21) NOT NULL
);


ALTER TABLE manerp.sysref_district OWNER TO manerp;

--
-- TOC entry 216 (class 1259 OID 29050)
-- Name: sysref_driving_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_driving_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_driving_type OWNER TO manerp;

--
-- TOC entry 217 (class 1259 OID 29055)
-- Name: sysref_gender; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_gender (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(3),
    last_updated timestamp without time zone,
    active boolean,
    name character varying(10) NOT NULL
);


ALTER TABLE manerp.sysref_gender OWNER TO manerp;

--
-- TOC entry 218 (class 1259 OID 29060)
-- Name: sysref_general_hazard_class; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_general_hazard_class (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(3),
    last_updated timestamp without time zone,
    active boolean,
    name character varying(13) NOT NULL
);


ALTER TABLE manerp.sysref_general_hazard_class OWNER TO manerp;

--
-- TOC entry 219 (class 1259 OID 29065)
-- Name: sysref_nace_code; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_nace_code (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(9),
    sysref_general_hazard_class_id character varying(32),
    last_updated timestamp without time zone,
    active boolean,
    name character varying(310) NOT NULL
);


ALTER TABLE manerp.sysref_nace_code OWNER TO manerp;

--
-- TOC entry 220 (class 1259 OID 29070)
-- Name: sysref_order_state; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_order_state (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_order_state OWNER TO manerp;

--
-- TOC entry 221 (class 1259 OID 29075)
-- Name: sysref_revenue_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_revenue_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_revenue_type OWNER TO manerp;

--
-- TOC entry 222 (class 1259 OID 29080)
-- Name: sysref_staff_contract_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_staff_contract_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_staff_contract_type OWNER TO manerp;

--
-- TOC entry 223 (class 1259 OID 29085)
-- Name: sysref_transportation_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_transportation_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_transportation_type OWNER TO manerp;

--
-- TOC entry 224 (class 1259 OID 29090)
-- Name: sysref_vehicle_owner; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_vehicle_owner (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_vehicle_owner OWNER TO manerp;

--
-- TOC entry 225 (class 1259 OID 29095)
-- Name: sysref_vehicle_state; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_vehicle_state (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_vehicle_state OWNER TO manerp;

--
-- TOC entry 226 (class 1259 OID 29100)
-- Name: sysref_vehicle_type; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_vehicle_type (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_vehicle_type OWNER TO manerp;

--
-- TOC entry 227 (class 1259 OID 29105)
-- Name: sysref_voyage_direction; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.sysref_voyage_direction (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8),
    last_updated timestamp without time zone,
    active boolean,
    sys_company_id character varying(32) NOT NULL,
    name character varying(100) NOT NULL,
    description character varying(255)
);


ALTER TABLE manerp.sysref_voyage_direction OWNER TO manerp;

--
-- TOC entry 228 (class 1259 OID 29110)
-- Name: user; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp."user" (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    first_name character varying(30) NOT NULL,
    is_deletable boolean NOT NULL,
    gsm_no character varying(15) NOT NULL,
    active boolean NOT NULL,
    account_expired boolean NOT NULL,
    photo_mime_type character varying(255),
    sysref_city_id character varying(32),
    sysref_country_id character varying(32),
    photo_name character varying(255),
    last_name character varying(50) NOT NULL,
    salt bytea,
    tc_id_number bigint NOT NULL,
    password_expired boolean NOT NULL,
    sysref_district_id character varying(32),
    last_updated timestamp without time zone,
    photo bytea,
    middle_name character varying(30),
    home_phone character varying(15),
    address character varying(255),
    password_hash bytea NOT NULL,
    sys_company_id character varying(32),
    username character varying(50) NOT NULL,
    account_locked boolean NOT NULL,
    birth_date timestamp without time zone,
    sysref_gender_id character varying(32),
    email character varying(255) NOT NULL
);


ALTER TABLE manerp."user" OWNER TO manerp;

--
-- TOC entry 229 (class 1259 OID 29118)
-- Name: vehicle; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.vehicle (
    id character varying(32) NOT NULL,
    fleet_card_number character varying(50) NOT NULL,
    sysref_vehicle_owner_id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    vehicle_owner_full_name character varying(50),
    active boolean,
    annual_insurance boolean,
    insurance_start_date timestamp without time zone,
    ogs_no character varying(50),
    sysref_vehicle_type_id character varying(32) NOT NULL,
    number_of_sensors smallint,
    km integer,
    is_dual_regime boolean NOT NULL,
    operation_insurance_notification boolean,
    fuel_kit character varying(50),
    ref_working_area_id character varying(32),
    kgs_no character varying(50),
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    plate_number character varying(12) NOT NULL,
    brand character varying(50) NOT NULL,
    insurance_end_date timestamp without time zone,
    sys_company_id character varying(32) NOT NULL,
    description character varying(255),
    purchase_date timestamp without time zone
);


ALTER TABLE manerp.vehicle OWNER TO manerp;

--
-- TOC entry 230 (class 1259 OID 29126)
-- Name: vehicle_document; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.vehicle_document (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    active boolean,
    document_name character varying(255) NOT NULL,
    document bytea NOT NULL,
    vehicle_id character varying(32) NOT NULL,
    document_mime_type character varying(255) NOT NULL
);


ALTER TABLE manerp.vehicle_document OWNER TO manerp;

--
-- TOC entry 231 (class 1259 OID 29134)
-- Name: vendor; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.vendor (
    id character varying(32) NOT NULL,
    phone character varying(20),
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    sysref_district_id character varying(32),
    last_updated timestamp without time zone,
    location_id character varying(32),
    active boolean,
    sysref_city_id character varying(32),
    title character varying(150) NOT NULL,
    sysref_country_id character varying(32),
    address character varying(255),
    company_id character varying(32) NOT NULL,
    sys_company_id character varying(32)
);


ALTER TABLE manerp.vendor OWNER TO manerp;

--
-- TOC entry 235 (class 1259 OID 29710)
-- Name: voyage; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.voyage (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    transport_waybill_no character varying(255),
    active boolean,
    substitude_driver_id character varying(32),
    trailer_id character varying(32),
    sysref_voyage_direction_id character varying(32) NOT NULL,
    sas_number character varying(255),
    loading_location_id character varying(32),
    sysref_transportation_type_id character varying(32),
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    dumping_location_id character varying(32),
    end_date timestamp without time zone,
    delivery_note_no character varying(255),
    sysref_delivery_status_id character varying(32),
    start_date timestamp without time zone NOT NULL,
    driver_id character varying(32) NOT NULL,
    vehicle_id character varying(32) NOT NULL,
    sys_company_id character varying(32) NOT NULL
);


ALTER TABLE manerp.voyage OWNER TO manerp;

--
-- TOC entry 232 (class 1259 OID 29150)
-- Name: voyage_order; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.voyage_order (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    code character varying(8) NOT NULL,
    last_updated timestamp without time zone,
    active boolean,
    voyage_id character varying(32) NOT NULL,
    order_id character varying(32) NOT NULL,
    sys_company_id character varying(32) NOT NULL
);


ALTER TABLE manerp.voyage_order OWNER TO manerp;

--
-- TOC entry 233 (class 1259 OID 29155)
-- Name: voyage_route; Type: TABLE; Schema: manerp; Owner: manerp
--

CREATE TABLE manerp.voyage_route (
    id character varying(32) NOT NULL,
    date_created timestamp without time zone,
    last_updated timestamp without time zone,
    location_id character varying(32) NOT NULL,
    active boolean,
    voyage_id character varying(32) NOT NULL
);


ALTER TABLE manerp.voyage_route OWNER TO manerp;

-- Completed on 2019-04-15 23:54:31 +03

--
-- PostgreSQL database dump complete
--

