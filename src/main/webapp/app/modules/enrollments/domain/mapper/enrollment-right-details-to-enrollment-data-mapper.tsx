import EnrollmentData from '../../enrollment-data';
import EnrollmentRightDetails from '../../../../shared/model/domain/dto/enrollment-right-details';

export default function mapEnrollmentRightDetailsToEnrollmentData(erd : EnrollmentRightDetails) {
    return new EnrollmentData(erd.id, erd.fieldOfStudy, erd.semester,
        erd.name, erd.rightSpecialty, erd.enrollmentsStartDate, erd.enrollmentsEndDate, erd.isPreEnrollment,
        erd.rightStartDate, erd.enrollmentUnits);
}