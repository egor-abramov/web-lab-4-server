package server.service.point;

import org.springframework.stereotype.Component;
import server.dto.request.point.PointRequest;
import server.exception.ValidationException;

import java.util.List;

@Component
class PointValidator {
    public void validate(PointRequest pointRequest) throws ValidationException {
        validateX(pointRequest.getX());
        validateY(pointRequest.getY());
        validateR(pointRequest.getR());
    }

    private void validateX(Float x) throws ValidationException {
        if (x < -3 || x > 5) {
            throw new ValidationException("X must be between -3 and 5");
        }
    }

    private void validateY(Float y) throws ValidationException {
        if (y < -5 || y > 3) {
            throw new ValidationException("Y must be between -5 and 3");
        }
    }

    private void validateR(Float r) throws ValidationException {
        List<Float> allowedR = List.of(1f, 2f, 3f, 4f, 5f);
        if (!allowedR.contains(r)) {
            throw new ValidationException("R must be in: " + allowedR);
        }
    }
}
