package Asteroids;

/**
 * Created with IntelliJ IDEA.
 * User: daacra
 * Reg: 1004888
 * Date: 31/05/13
 * Time: 18:15
 */

    // immutable 2D vectors
    public final class Vector2D {

        // fields
        public double x;
        public double y;

        // construct a null vector
        public Vector2D(){
            this(0, 0);
        }

        // construct a vector with given coordinates
        public Vector2D(double x, double y){
            this.x = x;
            this.y = y;
        }

        // construct a vector that is a copy of the argument
        public Vector2D(Vector2D v){
            x = v.x;
            y = v.y;
        }

        // set coordinates
        public Vector2D set(double x, double y){
            this.x = x;
            this.y = y;
            return this;
        }

        // set coordinates to argument vector coordinates
        public Vector2D set(Vector2D v){
            x = v.x;
            y = v.y;
            return this;
        }

        // compare for equality (needs to allow for Object type argument...)
        public boolean equals(Object o){
            if(o instanceof Vector2D){
                Vector2D v = (Vector2D)o;
                return ((v.x == x)
                &&(v.y == y));
            }else
                return false;
        }

        // magnitude (= "length") of this vector
        public double mag(){
            return Math.hypot(this.x, this.y);
            //return Math.sqrt(x*x + y*y);
        }

        // angle between vector and horizontal axis in radians
        public double theta(){
            return (Math.atan2(this.y, this.x));
        }

        // String for displaying vector as text
        public String toString(){
            return (".(x "+ x + " : " +  "y)" + y);
        }

        public Vector2D (String x, String y){
        this(Double.parseDouble(x), Double.parseDouble(y));
        }

        // add argument vector
        public Vector2D add(Vector2D v){
            return new Vector2D(this.x += v.x, this.y += v.y);

        }

        // add coordinate values
        public Vector2D add(double x, double y){
            this.x += x;
            this.y += y;
            return this;
        }

        // weighted add - frequently useful
        public Vector2D add(Vector2D v, double fac){
            return new Vector2D(x + fac*v.x, y + fac*v.y);
        }


        // subtract argument vector
        public Vector2D subtract(Vector2D v){
            this.x -= v.x;
            this.y -= v.y;
            return this;
        }

        // subtract coordinate values
        public Vector2D subtract(double x, double y){
            this.x -= x;
            this.y -= y;
            return this;
        }


        // multiply with factor
        public Vector2D mult(double fac){
            this.x *= fac;
            this.y *= fac;
            return this;
        }

        // "wrap" vector with respect to given positive values w & h
        // method assumes that x>= -w & y>= -h
        public Vector2D wrap(double w, double h){
            this.x += w;
            this.y += h;
            this.x = x % w;
            this.y = y % h;
            return this;
        }

        // rotate by angle given in radians
        public Vector2D rotate(double theta){

            double cosVal = Math.cos(theta);
            double sinVal = Math.sin(theta);
            double xVal = x * cosVal - y * sinVal;
            double yVal = x * sinVal + y * cosVal;
            x = xVal;
            y = yVal;
            return this;
        }

        // scalar product with argument vector
        public double scalarProduct(Vector2D v){
            return x * v.x + y * v.y;
        }

        // distance to argument vector
        public double dist(Vector2D v){
            //return Math.hypot(x - v.x, y - v.y);

            Vector2D curVec = new Vector2D (this);
            curVec.subtract(v);
            return curVec.mag();
           /* Vector2D b = new Vector2D(this);
            b.subtract(v);
            return b.mag();    */
            //double d1 = b.x - v.x;
            //double d2 = b.y = v.y;
            //return Math.sqrt(d1*d1 + d2*d2);
        }

        // normalise vector so that mag becomes 1
        // direction is unchanged
        public Vector2D normalise(){
            /*double len = mag();
            return new Vector2D(x / len, y / len); */
            double cTheta = this.theta();
            this.x = Math.cos(cTheta);
            this.y = Math.sin(cTheta);
            return this;
        }

        public Vector2D to(Vector2D v2) {
            return new Vector2D(v2.x-x, v2.y-y);

        }

        public Vector2D toWrapped(Vector2D v, double w, double h) {
            double dx = v.x - x;
            if(Math.abs(dx) > w/2) dx = dx - Math.signum(dx) * w;
            double dy = v.y - y;
            if(Math.abs(dy) > h/2) dy = dy - Math.signum(dy) * h;
            return new Vector2D(dx, dy);
        }

        public double distWrapped(Vector2D v, double w, double h){
            return this.toWrapped(v, w, h).mag();
        }

        public Vector2D proj(Vector2D d){
            Vector2D result = new Vector2D(d);
            result.mult(this.scalarProduct(d));
            return result;
        }
    }
