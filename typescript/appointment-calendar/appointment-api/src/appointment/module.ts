import { AppointmentController } from "./controller/appointment-controller";
import { AppointmentRepository } from "./repository/appointment-repository";

const repository = new AppointmentRepository();
const controller = new AppointmentController(repository);

export { controller };