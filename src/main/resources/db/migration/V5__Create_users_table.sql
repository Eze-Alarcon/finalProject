CREATE TABLE public.users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    enabled BOOLEAN NOT NULL DEFAULT TRUE,
    employee_id SMALLINT UNIQUE,
    CONSTRAINT fk_users_employees FOREIGN KEY (employee_id) REFERENCES public.employees(employee_id)
);

-- Migrar datos de acceso existentes de employees a users
INSERT INTO public.users (username, password, role, enabled, employee_id)
SELECT username, password_hash, role, COALESCE(activo, TRUE), employee_id
FROM public.employees
WHERE username IS NOT NULL;

-- Eliminar campos redundantes de la tabla employees
ALTER TABLE public.employees DROP COLUMN IF EXISTS username;
ALTER TABLE public.employees DROP COLUMN IF EXISTS password_hash;
ALTER TABLE public.employees DROP COLUMN IF EXISTS role;
ALTER TABLE public.employees DROP COLUMN IF EXISTS activo;
