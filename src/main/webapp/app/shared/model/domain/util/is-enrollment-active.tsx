import EnrollmentData from "../../../../modules/enrollments/enrollment-data";

const isEnrollmentActive = (ed: EnrollmentData, currentDate: Date) => {
    return ed.enrollmentUnits.some(unit => currentDate >= ed.rightStartDate
        && currentDate >= unit.startDate && currentDate <= unit.endDate)
}

export default isEnrollmentActive;