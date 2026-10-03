import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";
import { useAuth } from "../context/AuthContext";

function Profile() {
  const { user } = useAuth();
  const [profile, setProfile] = useState(null);
  const [fullName, setFullName] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    const load = async () => {
      try {
        setLoading(true);
        setError("");
        const response = await api.get("/profile");
        setProfile(response.data);
        setFullName(response.data.fullName || "");
      } catch (err) {
        setError(err.response?.data?.message || "Could not load profile.");
      } finally {
        setLoading(false);
      }
    };

    load();
  }, []);

  const save = async (e) => {
    e.preventDefault();
    setSaving(true);
    setError("");
    setSuccess("");

    try {
      const response = await api.put("/profile", { fullName });
      setProfile(response.data);
      setSuccess("Profile updated.");
    } catch (err) {
      setError(err.response?.data?.message || "Could not update profile.");
    } finally {
      setSaving(false);
    }
  };

  return (
    <main className="mx-auto max-w-4xl px-6 py-12">
      <h1 className="text-4xl font-black text-slate-900">Profile</h1>
      <p className="mt-2 text-slate-500">
        Manage your account details and jump back into bookings or groups.
      </p>

      {loading && <p className="mt-8 text-slate-500">Loading profile...</p>}

      {error && (
        <div className="mt-6 rounded-xl bg-red-50 p-4 text-red-600">{error}</div>
      )}

      {success && (
        <div className="mt-6 rounded-xl bg-emerald-50 p-4 text-emerald-700">
          {success}
        </div>
      )}

      {profile && (
        <div className="mt-8 rounded-2xl border border-slate-200 bg-white p-8 shadow-sm">
          <div className="flex h-20 w-20 items-center justify-center rounded-full bg-indigo-100 text-3xl font-black text-indigo-600">
            {profile.fullName?.charAt(0)}
          </div>

          <form onSubmit={save} className="mt-6 space-y-4">
            <input
              value={fullName}
              onChange={(e) => setFullName(e.target.value)}
              className="w-full rounded-xl border border-slate-300 px-4 py-3"
              required
              minLength={2}
            />
            <p className="text-slate-500">{profile.email}</p>
            <button
              disabled={saving}
              className="rounded-xl bg-indigo-600 px-5 py-3 font-bold text-white hover:bg-indigo-700 disabled:opacity-50"
            >
              {saving ? "Saving..." : "Save changes"}
            </button>
          </form>

          <div className="mt-6 grid gap-4 sm:grid-cols-2">
            <div className="rounded-xl bg-slate-50 p-4">
              <p className="text-xs font-bold uppercase text-slate-400">Role</p>
              <p className="mt-1 font-semibold">{profile.role}</p>
            </div>
            <div className="rounded-xl bg-slate-50 p-4">
              <p className="text-xs font-bold uppercase text-slate-400">
                Account
              </p>
              <p className="mt-1 font-semibold">
                {profile.enabled ? "Active" : "Disabled"}
              </p>
            </div>
          </div>

          <div className="mt-8 flex flex-wrap gap-3">
            <Link
              to="/bookings"
              className="rounded-xl bg-slate-900 px-4 py-2 text-sm font-semibold text-white"
            >
              Booking history
            </Link>
            <Link
              to="/saved"
              className="rounded-xl border border-slate-200 px-4 py-2 text-sm font-semibold"
            >
              Saved experiences
            </Link>
            <Link
              to="/groups"
              className="rounded-xl border border-slate-200 px-4 py-2 text-sm font-semibold"
            >
              Group Converge
            </Link>
          </div>

          {user?.email && (
            <p className="mt-6 text-xs text-slate-400">
              Signed in as {user.email}
            </p>
          )}
        </div>
      )}
    </main>
  );
}

export default Profile;
