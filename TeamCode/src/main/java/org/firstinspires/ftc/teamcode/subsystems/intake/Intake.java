package org.firstinspires.ftc.teamcode.subsystems.intake;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import javax.management.ObjectName;

public class Intake extends SubsystemBase {
    public enum STATES{
        INTAKE, TRANSFER, OFF
    }
    private STATES currentState = STATES.OFF;

    private Telemetry telemetry;
    private HardwareMap hMap;

    private DcMotorEx intake1;
    private DcMotorEx intake2;

    public Intake(HardwareMap hMap, Telemetry telemetry){
        this.hMap = hMap;
        this.telemetry = telemetry;
        this.init();
    }
    private void init(){
        intake1 = hMap.get(DcMotorEx.class, IntakeConstants.hmIntake1);
        intake2 = hMap.get(DcMotorEx.class, IntakeConstants.hmIntake2);

        intake1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        intake2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    private void setMotorsPower(double power){
        intake1.setPower(power);
        intake2.setPower(power);
    }
    public void setState(STATES state){
        this.currentState = state;
    }
    @Override
    public void periodic(){
        switch (currentState){
            case INTAKE:
                setMotorsPower(IntakeConstants.intakeSpeed);
                break;
            case TRANSFER:
                setMotorsPower(IntakeConstants.transferSpeed);
                break;
            case OFF:
                setMotorsPower(0);
                break;
        }
        telemetry.addData("Intake state: ", currentState);
    }




    public class IntakeOnCommand extends CommandBase {
        private Intake intake;
        public IntakeOnCommand(Intake intake){
            this.intake = intake;
            addRequirements(intake);
        }
        @Override
        public void initialize(){
            this.intake.setState(STATES.INTAKE);
        }
        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class IntakeOffCommand extends CommandBase {
        private Intake intake;
        public IntakeOffCommand(Intake intake){
            this.intake = intake;
            addRequirements(intake);
        }
        @Override
        public void initialize(){
            this.intake.setState(STATES.OFF);
        }
        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class IntakeTransferCommand extends CommandBase {
        private Intake intake;
        public IntakeTransferCommand(Intake intake){
            this.intake = intake;
            addRequirements(intake);
        }
        @Override
        public void initialize(){
            this.intake.setState(STATES.TRANSFER);
        }
        @Override
        public boolean isFinished() {
            return true;
        }
    }



}
