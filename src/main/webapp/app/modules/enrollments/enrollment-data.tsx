import EnrollmentUnit from "../../shared/model/domain/dto/enrollment-unit";

export default class EnrollmentData {
    public readonly id: number;
    public readonly fieldOfStudy: string;
    public readonly semester: number;
    public readonly year: number;
    public readonly name: string;
    public readonly speciality: string;
    public readonly startDate: Date;
    public readonly endDate: Date;
    public readonly isPreEnrollment: boolean;
    public readonly rightStartDate: Date;
    public readonly enrollmentUnits: Array<EnrollmentUnit>;
    public readonly isActive: () => boolean;

    constructor(id: number, fieldOfStudy: string, semester: number, name: string, speciality: string,
        startDate: Date, endDate: Date, isPreEnrollment: boolean, rightStartDate: Date, enrollmentUnits: Array<EnrollmentUnit>) {
        startDate = new Date(Date.parse(startDate.toString()));
        endDate = new Date(Date.parse(endDate.toString()));
        rightStartDate = new Date(Date.parse(rightStartDate.toString()));
        this.id = id;
        this.fieldOfStudy = fieldOfStudy;
        this.semester = semester;
        this.year = startDate.getFullYear();
        this.name = name;
        this.speciality = speciality;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isPreEnrollment = isPreEnrollment;
        this.rightStartDate = rightStartDate;
        this.enrollmentUnits = enrollmentUnits;
        this.isActive = this.checkIfActive;
    }

    checkIfActive(): boolean {
        const currentDate = new Date();
        return this.enrollmentUnits.some(unit => currentDate >= this.rightStartDate
            && currentDate >= unit.startDate && currentDate <= unit.endDate)
    }
}