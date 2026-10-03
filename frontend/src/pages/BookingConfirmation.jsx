import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../services/api";

function BookingConfirmation() {
  const { bookingReference } = useParams();
  const [booking, setBooking] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        const response = await api.get(`/bookings/${bookingReference}`);
        setBooking(response.data);
      } catch (err) {
        setError(
          err.response?.data?.message || "Could not load this booking."
        );
      } finally {
        setLoading(false);
      }
    };

    load();
  }, [bookingReference]);

  if (loading) {
    return (
      <main className="mx-auto max-w-3xl px-6 py-16 text-center text-slate-500">
        Loading confirmation...
      </main>
    );
  }

  if (error) {
    return (
      <main className="mx-auto max-w-3xl px-6 py-16">
        <div className="rounded-2xl border border-red-200 bg-red-50 p-6 text-red-700">
          {error}
        </div>
      </main>
    );
  }

  return (
    <main className="mx-auto max-w-3xl px-6 py-12">
      <div className="rounded-3xl border border-emerald-200 bg-white p-8 shadow-sm">
        <p className="text-sm font-bold uppercase tracking-wider text-emerald-600">
          Booking confirmed
        </p>
        <h1 className="mt-2 text-4xl font-black text-slate-900">
          {booking.experienceTitle}
        </h1>
        <p className="mt-3 text-lg font-black tracking-widest text-slate-700">
          {booking.bookingReference}
        </p>

        <div className="mt-8 grid gap-4 sm:grid-cols-2">
          <div className="rounded-xl bg-slate-50 p-4">
            <p className="text-xs font-bold uppercase text-slate-400">When</p>
            <p className="mt-1 font-semibold">
              {booking.scheduleDate} · {booking.startTime}
            </p>
          </div>
          <div className="rounded-xl bg-slate-50 p-4">
            <p className="text-xs font-bold uppercase text-slate-400">Where</p>
            <p className="mt-1 font-semibold">
              {booking.venueName}, {booking.city}
            </p>
          </div>
          <div className="rounded-xl bg-slate-50 p-4">
            <p className="text-xs font-bold uppercase text-slate-400">Seats</p>
            <p className="mt-1 font-semibold">{booking.seatCount}</p>
          </div>
          <div className="rounded-xl bg-slate-50 p-4">
            <p className="text-xs font-bold uppercase text-slate-400">Total</p>
            <p className="mt-1 font-semibold">₹{booking.totalAmount}</p>
          </div>
        </div>

        <div className="mt-8 flex flex-wrap gap-3">
          <Link
            to="/bookings"
            className="rounded-xl bg-slate-900 px-5 py-3 font-bold text-white"
          >
            View all bookings
          </Link>
          <Link
            to="/explore"
            className="rounded-xl border border-slate-200 px-5 py-3 font-bold"
          >
            Book another
          </Link>
        </div>
      </div>
    </main>
  );
}

export default BookingConfirmation;
