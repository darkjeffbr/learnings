import { Request, Response } from "express";
import { Appointment } from "../model/appointment";
import { AppointmentRepository } from "../repository/appointment-repository";

export class AppointmentController {

    constructor(private repository: AppointmentRepository) {
    }

    getAll(req: Request, res: Response) {
        const appointments = this.repository.findAll();
        if(appointments.length<=0){
            res.status(204).send(appointments);
        }else{
            res.status(200).send(appointments);
        }        
    }

    save(req: Request, res: Response) {
        const appointment: Appointment = req.body as Appointment;
        const savedAppointment = this.repository.save(appointment);

        const location = `${req.originalUrl}/${savedAppointment.id}`;

        res.status(201)            
            .header('Location', location)
            .send(savedAppointment);
    }

    getById(req: Request, res: Response) {
        const id: number = parseInt(req.params.id);
        const appointment: Appointment = this.repository.findById(id);
        if(appointment){
            res.status(200).send(appointment);
            return;
        }
        res.status(404).send();
    }

    update(req: Request, res: Response) {
        const id: number = parseInt(req.params.id);
        const appointment: Appointment = req.body as Appointment;
        appointment.id = id;
        this.repository.update(id, appointment);
        res.status(200).send();
    }

}