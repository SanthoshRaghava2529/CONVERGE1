import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

function Bookings() {
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const response = await api.get("/bookings/user");
        setBookings(response.data || []);
      } catch (err) {
        setError(err.response?.data?.message || "Could not load bookings.");
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  return (
    <main className="mx-auto max-w-5xl px-6 py-12">
      <h1 className="text-4xl font-black text-slate-900">Booking history</h1>
      <p className="mt-2 text-slate-500">
        Your confirmed tickets and group bookings.
      </p>

      {loading && <p className="mt-8 text-slate-500">Loading bookings...</p>}

      {error && (
        <div className="mt-6 rounded-xl bg-red-50 p-4 text-red-600">{error}</div>
      )}

      {!loading && !error && bookings.length === 0 && (
        <div className="mt-10 rounded-2xl border border-dashed border-slate-300 py-16 text-center">
          <h2 className="text-xl font-bold">No bookings yet</h2>
          <p className="mt-2 text-slate-500">
            Explore experiences and book a schedule to see it here.
          </p>
          <Link
            to="/explore"
            className="mt-5 inline-block rounded-xl bg-indigo-600 px-5 py-3 font-bold text-white"
          >
            Explore experiences
          </Link>
        </div>
      )}

      <div className="mt-8 grid gap-4">
        {bookings.map((booking) => (
          <Link
            key={booking.id}
            to={`/bookings/${booking.bookingReference}`}
            className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm hover:border-indigo-300"
          >
            <div className="flex flex-wrap items-start justify-between gap-4">
              <div>
                <p className="text-xs font-bold uppercase text-indigo-600">
                  {booking.bookingReference}
                </p>
                <h2 className="mt-1 text-xl font-black">
                  {booking.experienceTitle}
                </h2>
                <p className="mt-2 text-sm text-slate-500">
                  {booking.scheduleDate} · {booking.startTime} · {booking.city}
                </p>
              </div>
              <div className="text-right">
                <p className="font-black">₹{booking.totalAmount}</p>
                <p className="text-sm text-slate-500">
                  {booking.seatCount} seats · {booking.status}
                </p>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </main>
  );
}

export default Bookings;
