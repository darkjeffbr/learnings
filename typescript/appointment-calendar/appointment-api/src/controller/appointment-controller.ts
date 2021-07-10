import { Request, Response } from "express";
import { Appointment } from "../model/appointment";

const appointments: Appointment[] = [
    {
        title: 'Medical appointment',
        notes: [],
        allDay: false,
        start: new Date(),
        end: new Date(),
        date: new Date()
    },
    {
        title: 'Supermarket',
        notes: [
            'milk',
            'bread'
        ],
        allDay: false,
        start: new Date(),
        end: new Date(),
        date: new Date()
    }
];

export const getAll = (req: Request, res: Response) => {
    res.status(200).send(appointments);
};