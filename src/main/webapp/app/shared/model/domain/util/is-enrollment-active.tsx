import EnrollmentData from "../../../../modules/enrollments/enrollment-data";

const isEnrollmentActive = (ed: EnrollmentData, currentDate: Date) => {
    return currentDate >= new Date(Date.parse(ed.rightStartDate.toString())) && ed.enrollmentUnits.some(unit =>
        currentDate >= new Date(Date.parse(unit.startDate.toString())) && currentDate <= new Date(Date.parse(unit.endDate.toString())))
}

export default isEnrollmentActive;