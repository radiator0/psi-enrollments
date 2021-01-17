import React from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type IAsksForEnrollmentOverLimitProps = StateProps;

export const AsksForEnrollmentOverLimit = (props: IAsksForEnrollmentOverLimitProps) => {

  return (
    <Row>
      Asks for enrollment over limit
    </Row>
  );
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(AsksForEnrollmentOverLimit);
