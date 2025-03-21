CREATE TABLE test.entreprise (
    id integer NOT NULL,
    rue character varying(255),
    numero character varying(255),
    pays character varying(255),
    type_voie character varying(255),
    ville character varying(255),
    date_creation date,
    denomination character varying(255),
    forme_juridique character varying(255),
    siren character varying(9) NOT NULL,
    telephone character varying(10)
);


ALTER TABLE test.entreprise OWNER TO postgres;

--
-- TOC entry 2818 (class 0 OID 42849)
-- Dependencies: 204
-- Data for Name: entreprise; Type: TABLE DATA; Schema: test; Owner: postgres
--

INSERT INTO test.entreprise VALUES (1, 'Jean Jaurès', '12', 'FRANCE', 'RUE', 'PARIS', '2014-03-01', 'Le bar à Momo', 'SARL', '123456789', '0123456789');
INSERT INTO test.entreprise VALUES (2, 'Charles de Gaulle', '56', 'FRANCE', 'BOULEVARD', 'RENNES', '2015-05-26', 'Confiserie', 'INDIV', '234567891', '0234567890');
INSERT INTO test.entreprise VALUES (3, 'Alfred Alfred', '23', 'FRANCE', 'IMPASSE', 'NANTES', '2012-03-24', 'Le p''tit nantais', 'SNC', '345678912', '0245678912');


--
-- TOC entry 2689 (class 2606 OID 42856)
-- Name: entreprise entreprise_pkey; Type: CONSTRAINT; Schema: test; Owner: postgres
--

ALTER TABLE ONLY test.entreprise
    ADD CONSTRAINT entreprise_pkey PRIMARY KEY (id);


--
-- TOC entry 2691 (class 2606 OID 42858)
-- Name: entreprise uk_ep28ukm4mifb7h3pelb50fj83; Type: CONSTRAINT; Schema: test; Owner: postgres
--

ALTER TABLE ONLY test.entreprise
    ADD CONSTRAINT uk_ep28ukm4mifb7h3pelb50fj83 UNIQUE (siren);


-- Completed on 2021-06-29 11:49:35

--
-- PostgreSQL database dump complete
--

