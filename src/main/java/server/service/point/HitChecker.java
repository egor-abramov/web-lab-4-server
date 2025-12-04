package server.service.point;

import org.springframework.stereotype.Component;
import server.dto.request.point.PointRequest;

@Component
class HitChecker {
    public boolean checkHit(PointRequest point) {
        return checkHitToCircle(point) || checkHitToRectangle(point) || checkHitToTriangle(point);
    }

    private boolean checkHitToCircle(PointRequest point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        return x <= 0 && y <= 0 && 4 * (x * x + y * y) <= r * r;
    }

    private boolean checkHitToRectangle(PointRequest point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        return x >= 0 && y <= 0 && 2 * x <= r && y >= -r;
    }

    private boolean checkHitToTriangle(PointRequest point) {
        double x = point.getX();
        double y = point.getY();
        double r = point.getR();
        return x >= 0 && y >= 0 && y <= -x + r;
    }
}