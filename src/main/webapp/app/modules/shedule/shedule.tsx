import React from 'react';
import { connect } from 'react-redux';
import { Row } from 'reactstrap';

export type ISheduleProps = StateProps;

export const Shedule = (props: ISheduleProps) => {

  return (
    <Row>
      Shedule
    </Row>
  );
};

const mapStateToProps = () => ({
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Shedule);
