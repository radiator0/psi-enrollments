import EnrollmentUnit from './enrollment-unit';


export default class EnrollmentRightDetails {
  public readonly id: number;
  public readonly name: string;
  public readonly isPreEnrollment: boolean;
  public readonly enrollmentsStartDate: Date;
  public readonly enrollmentsEndDate: Date;
  public readonly enrollmentUnits: Array<EnrollmentUnit>;
  public readonly fieldOfStudy: string;
  public readonly semester: number;
  public readonly rightStartDate: Date;
  public readonly rightSpecialty: string;

  constructor(id: number, name: string, isPreEnrollment: boolean,
    enrollmentsStartDate: Date, enrollmentsEndDate: Date, enrollmentUnits: Array<EnrollmentUnit>, 
    fieldOfStudy: string, semester: number, rightStartDate: Date, rightSpecialty: string) {
      this.id = id;
      this.name = name;
      this.isPreEnrollment = isPreEnrollment;
      this.enrollmentsStartDate = enrollmentsStartDate;
      this.enrollmentsEndDate = enrollmentsEndDate;
      this.enrollmentUnits = enrollmentUnits;
      this.fieldOfStudy = fieldOfStudy;
      this.semester = semester;
      this.rightStartDate = rightStartDate;
      this.rightSpecialty = rightSpecialty;
  }
};