-- Create ENUM type 'result_type' only if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'result_type') THEN
CREATE TYPE public.result_type AS ENUM (
            'Success',
            'Redo',
            'Error',
            'Fatal',
            'Success but not imported yet'
        );
END IF;
END $$;

-- Create ENUM type 'detailed_status' only if it doesn't exist
DO
$$
BEGIN
	IF
NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'detailed_status') THEN
CREATE TYPE public.detailed_status AS ENUM ();
END IF;
END $$;

DO
$$
BEGIN
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Found';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Unzipped';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'S3 Uploading';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Uploaded S3';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Sent for JPS import';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Received import acknowledgement';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Import completed';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Downloaded result log';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Sent result log';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Cleaned';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Error during JPS Import';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Error during S3 Upload';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Error during result log download';
ALTER TYPE detailed_status ADD VALUE IF NOT EXISTS 'Error during cleanup';
END $$;

-- Create ENUM type 'status' only if it doesn't exist
DO
$$
BEGIN
	IF
NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'status') THEN
CREATE TYPE public.status AS ENUM ();
END IF;
END $$;

DO
$$
BEGIN
ALTER TYPE status ADD VALUE IF NOT EXISTS 'PES package preparing';
ALTER TYPE status ADD VALUE IF NOT EXISTS 'JPS import';
ALTER TYPE status ADD VALUE IF NOT EXISTS 'S3 Uploading';
ALTER TYPE status ADD VALUE IF NOT EXISTS 'PES result processing';
ALTER TYPE status ADD VALUE IF NOT EXISTS 'Cleanup';
ALTER TYPE status ADD VALUE IF NOT EXISTS 'Error';
END $$;

CREATE SEQUENCE IF NOT exists public.package_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE if not exists public.package
(
    id
    bigint
    DEFAULT
    nextval
(
    'public.package_id_seq'
) NOT NULL,
    name character varying NOT NULL,
    file_system_location character varying NOT NULL,
    s3_location character varying,
    backup_location character varying,
    status public.status NOT NULL,
    detailed_status public.detailed_status NOT NULL,
    created timestamp without time zone NOT NULL,
    modified timestamp
                      without time zone NOT NULL,
    vendor character varying NOT NULL,
    package_name_timestamp timestamp
                      without time zone NOT null,
    clean_after_date timestamp
                      without time zone
    );

CREATE SEQUENCE IF NOT exists public.package_audit_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT exists public.package_audit
(
    id
    bigint
    DEFAULT
    nextval
(
    'public.package_audit_id_seq'
) NOT NULL,
    package_id bigint NOT NULL,
    package_name character varying NOT NULL,
    created timestamp without time zone NOT NULL,
    description character varying
    );


CREATE sequence IF NOT EXISTS public.result_log_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE IF NOT EXISTS public.result_log
(
    id
    bigint
    DEFAULT
    nextval
(
    'public.result_log_id_seq'
) NOT NULL,
    package_id bigint NOT NULL,
    type public.result_type NOT NULL,
    file_system_location character varying,
    s3_location character varying,
    created timestamp without time zone NOT NULL,
    modified timestamp
                      without time zone NOT NULL,
    package_name character varying NOT NULL,
    log_link character varying
    );

-- Conditionally add primary key constraint to 'package' if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'package_pk' AND table_name = 'package'
    ) THEN
ALTER TABLE public.package
    ADD CONSTRAINT package_pk PRIMARY KEY (id);
END IF;
END $$;

-- Conditionally add primary key constraint to 'package_audit' if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'packageaudit_pk' AND table_name = 'package_audit'
    ) THEN
ALTER TABLE public.package_audit
    ADD CONSTRAINT packageaudit_pk PRIMARY KEY (id);
END IF;
END $$;

-- Conditionally add primary key constraint to 'result_log' if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'result_log_pk' AND table_name = 'result_log'
    ) THEN
ALTER TABLE public.result_log
    ADD CONSTRAINT result_log_pk PRIMARY KEY (id);
END IF;
END $$;

-- Conditionally add foreign key constraint to 'package_audit' if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'packageaudit_fk' AND table_name = 'package_audit'
    ) THEN
ALTER TABLE public.package_audit
    ADD CONSTRAINT packageaudit_fk FOREIGN KEY (package_id) REFERENCES public.package (id);
END IF;
END $$;

-- Conditionally add foreign key constraint to 'result_log' if it doesn't exist
DO
$$
BEGIN
    IF
NOT EXISTS (
        SELECT 1
        FROM information_schema.table_constraints
        WHERE constraint_name = 'result_log_fk' AND table_name = 'result_log'
    ) THEN
ALTER TABLE public.result_log
    ADD CONSTRAINT result_log_fk FOREIGN KEY (package_id) REFERENCES public.package (id);
END IF;
END $$;

endOfScript
