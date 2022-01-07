import { Schema } from "mongoose";
import { Appointment } from "../../model/appointment";
import mongoose from "mongoose";

const AppointmentSchema = new Schema<Appointment>({
    title: { type: String, required: true },
    date: { type: Date, required: true }
});

const AppointmentModel = mongoose.model<Appointment>('Appointment', AppointmentSchema);

export { AppointmentModel }