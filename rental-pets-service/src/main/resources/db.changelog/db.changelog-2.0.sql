--liquibase formatted sql

--changeset author:1
ALTER TABLE webapp.bookings DROP CONSTRAINT IF EXISTS bookings_user_id_fkey;
ALTER TABLE webapp.bookings ADD CONSTRAINT bookings_user_id_fkey FOREIGN KEY (user_id) REFERENCES webapp.users (id) ON DELETE CASCADE;
--rollback ALTER TABLE webapp.bookings DROP CONSTRAINT bookings_user_id_fkey;

--changeset author:12
ALTER TABLE webapp.notifications DROP CONSTRAINT IF EXISTS notifications_booking_id_fkey;
ALTER TABLE webapp.notifications ADD CONSTRAINT notifications_booking_id_fkey FOREIGN KEY (booking_id) REFERENCES webapp.bookings (id) ON DELETE CASCADE;
--rollback ALTER TABLE webapp.notifications DROP CONSTRAINT notifications_booking_id_fkey;

--changeset author:3
ALTER TABLE webapp.notifications DROP CONSTRAINT IF EXISTS notifications_user_id_fkey;
ALTER TABLE webapp.notifications ADD CONSTRAINT notifications_user_id_fkey FOREIGN KEY (user_id) REFERENCES webapp.users (id) ON DELETE CASCADE;
--rollback ALTER TABLE webapp.notifications DROP CONSTRAINT notifications_user_id_fkey;
