import React from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type IEnrollmentsProps = StateProps;

export const Enrollments = (props: IEnrollmentsProps) => {

  return (
    <Row>
      Enrollments
    </Row>
  );
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Enrollments);
