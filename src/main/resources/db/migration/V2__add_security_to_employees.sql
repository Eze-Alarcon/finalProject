-- Columnas necesarias para Spring Security
ALTER TABLE public.employees
    ADD COLUMN username VARCHAR(50) UNIQUE,
ADD COLUMN password_hash VARCHAR(255),
ADD COLUMN role VARCHAR(50),
ADD COLUMN activo BOOLEAN DEFAULT true;

-- un rol por defecto para que no den error al mapear la entidad en Java.
UPDATE public.employees
SET role = 'ROLE_WAREHOUSE', activo = true
WHERE role IS NULL;